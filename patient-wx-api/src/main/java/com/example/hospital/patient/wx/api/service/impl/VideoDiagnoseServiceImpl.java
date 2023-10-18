package com.example.hospital.patient.wx.api.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.example.hospital.patient.wx.api.db.dao.UserDao;
import com.example.hospital.patient.wx.api.db.dao.VideoDiagnoseDao;
import com.example.hospital.patient.wx.api.db.pojo.VideoDiagnoseEntity;
import com.example.hospital.patient.wx.api.service.PaymentService;
import com.example.hospital.patient.wx.api.service.VideoDiagnoseService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author Shiqi
 */
@Service
@Slf4j
public class VideoDiagnoseServiceImpl implements VideoDiagnoseService {
    @Resource
    private RedisTemplate<String, Boolean> redisTemplate;

    @Override
    public ArrayList<HashMap> searchOnlineDoctorList(String subName, String job) {
        ArrayList<HashMap> list = new ArrayList<>();
        //Find doctor in cache
        Set<String> keys = redisTemplate.keys("online_doctor_*");
        for (String key : keys) {
            Map entries = redisTemplate.opsForHash().entries(key);
            String tempSubName = MapUtil.getStr(entries, "subName");
            String tempJob = MapUtil.getStr(entries, "job");
            boolean open = MapUtil.getBool(entries, "open");
            if (!open) {
                continue;
            }
            //Filter out doctors who are not in the specified clinic
            if (subName != null && !subName.equals(tempSubName)) {
                continue;
            }
            //Filter out doctors who do not have the required professional titles
            if (job != null && !job.equals(tempJob)) {
                continue;
            }
            HashMap map = new HashMap() {{
                put("doctorId", MapUtil.getInt(entries, "doctorId"));
                put("name", MapUtil.getStr(entries, "name"));
                put("photo", MapUtil.getStr(entries, "photo"));
                put("job", MapUtil.getStr(entries, "job"));
                put("description", MapUtil.getStr(entries, "description"));
                put("remark", MapUtil.getStr(entries, "remark"));
                put("price", MapUtil.getStr(entries, "price"));
            }};
            list.add(map);
        }
        return list;
    }

    @Resource
    private UserDao userDao;

    @Resource
    private VideoDiagnoseDao videoDiagnoseDao;

    @Resource
    private PaymentService paymentService;

    private String notifyUrl = "/video_diagnose/transactionCallback";

    @Override
    public HashMap createVideoDiagnose(int userId, VideoDiagnoseEntity entity) {
        HashMap<String, java.io.Serializable> result = new HashMap<>();

        HashMap map = userDao.searchOpenId(userId);
        String openId = MapUtil.getStr(map, "openId");
        int patientCardId = MapUtil.getInt(map, "patientCardId");
        entity.setPatientCardId(patientCardId);

        String key = "online_doctor_" + entity.getDoctorId();
        //Get the doctor's registration amount from the cache
        String price = redisTemplate.opsForHash().get(key, "price").toString();
        int amount = new BigDecimal(price).multiply(new BigDecimal(100)).intValue();
        entity.setAmount(new BigDecimal(price));

        //Try to register with Redis transaction
        boolean execute = (Boolean) redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //Get the version cached by the doctor online
                operations.watch(key);
                //Get doctor online cache data
                Map entries = operations.opsForHash().entries(key);
                boolean open = MapUtil.getBool(entries, "open");
                //If the doctor does not enable registration, registration will not be possible
                if (!open) {
                    return false;
                }
                String nextPatient = MapUtil.getStr(entries, "nextPatient");
                //If there is a waiting patient, registration cannot be done (there can only be one waiting patient)
                if (!"none".equals(nextPatient)) {
                    return false;
                }
                try {
                    //Start Redis transaction
                    operations.multi();
                    //Register yourself as a waiting patient
                    operations.opsForHash().put(key, "nextPatient", patientCardId);
                    //commit transaction
                    operations.exec();
                    return true;
                } catch (Exception e) {
                    log.debug("Transaction submission failed", e);
                    return false;
                }
            }
        });

        result.put("flag", execute);
        if (!execute) {
            return result;
        }

        DateTime now = new DateTime();
        //Get doctor online cache data
        Map entries = redisTemplate.opsForHash().entries(key);
        //Current consultation patient ID
        String currentPatient = MapUtil.getStr(entries, "currentPatient");

        String expectStart = null;
        String expectEnd = null;
        //Define estimated start and end times if there is no current patient consultation
        if ("none".equals(currentPatient)) {
            expectStart = now.offsetNew(DateField.MINUTE, 1).toString("yyyy-MM-dd HH:mm:ss");
            expectEnd = now.offsetNew(DateField.MINUTE, 16).toString("yyyy-MM-dd HH:mm:ss");
        }
        //If there is a current consultation patient, the estimated start and end time will be based on the current consultation end time.
        else {
            DateTime currentEnd = new DateTime(MapUtil.getStr(entries, "currentEnd"));
            expectStart = currentEnd.offsetNew(DateField.MINUTE, 1).toString("yyyy-MM-dd HH:mm:ss");
            expectEnd = currentEnd.offsetNew(DateField.MINUTE, 16).toString("yyyy-MM-dd HH:mm:ss");
        }

        entity.setExpectStart(expectStart);
        entity.setExpectEnd(expectEnd);

        String outTradeNo = IdUtil.simpleUUID().toUpperCase();
        //Set the payment order expiration time to 1 minute. If payment is not made within this time, the payment order will be automatically closed.
        String timeExpire = now.offsetNew(DateField.MINUTE, 1).toString("yyyy-MM-dd'T'HH:mm:ss'+08:00'");

        //Generate WeChat payment order
        ObjectNode objectNode = paymentService.unifiedOrder(outTradeNo, openId, amount, "Registration fee", notifyUrl, timeExpire);
        String prepayId = objectNode.get("prepay_id").textValue();  //Prepaid order ID

        entity.setOutTradeNo(outTradeNo);
        entity.setPrepayId(prepayId);

        //Save video consultation registration records
        videoDiagnoseDao.insert(entity);

        //Query the primary key value of video consultation records
        HashMap data = videoDiagnoseDao.searchByOutTradeNo(outTradeNo);
        int id = MapUtil.getInt(data, "id");

        HashMap<String, java.io.Serializable> cache = new HashMap<>();
        cache.put("nextOrder", id);
        cache.put("nextPayment", false);
        cache.put("nextStart", expectStart);
        cache.put("nextEnd", expectEnd);
        cache.put("nextNotify", false);
        //Update cached data
        redisTemplate.opsForHash().putAll(key, cache);

        //Information related to WeChat payment
        result.put("outTradeNo", outTradeNo);
        result.put("prepayId", prepayId);
        result.put("timeStamp", objectNode.get("timeStamp").asText());
        result.put("nonceStr", objectNode.get("nonceStr").asText());
        result.put("package", objectNode.get("package").asText());
        result.put("signType", objectNode.get("signType").asText());
        result.put("paySign", objectNode.get("paySign").asText());
        result.put("videoDiagnoseId", id);
        result.put("expectStart", expectStart);
        result.put("expectEnd", expectEnd);

        //Create a payment cache and destroy it after 1 minute
        String paymentKey = "patient_video_diagnose_payment#" + id;
        redisTemplate.opsForValue().set(paymentKey, false);
        //It takes a short time to receive payment notification messages and actively query payment results, so the cache expiration time must be set to greater than 60 seconds.
        redisTemplate.expireAt(paymentKey, now.offset(DateField.SECOND, 90));
        return result;

    }
}

