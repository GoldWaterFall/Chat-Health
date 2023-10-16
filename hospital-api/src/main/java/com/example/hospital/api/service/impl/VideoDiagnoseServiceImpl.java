package com.example.hospital.api.service.impl;

import cn.hutool.core.map.MapUtil;
import com.example.hospital.api.db.dao.DoctorDao;
import com.example.hospital.api.db.dao.MisUserDao;
import com.example.hospital.api.exception.HospitalException;
import com.example.hospital.api.service.VideoDiagnoseService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Shiqi
 */
@Service
public class VideoDiagnoseServiceImpl implements VideoDiagnoseService {
    @Resource
    private DoctorDao doctorDao;

    @Resource
    private MisUserDao misUserDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void online(int userId) {
        //Find the doctor's userId and query the doctorId. Because this operation often occurs, I defined a wrapper function
        int doctorId = this.searchDoctorId(userId);

        String key = "online_doctor_" + doctorId;
        //Determine whether there is an online cache for the doctor
        if (redisTemplate.hasKey(key)) {
            return;
        }
        //Query doctor details
        HashMap map = doctorDao.searchDataForOnlineCache(doctorId);
        //Create a doctor online cache
        redisTemplate.opsForHash().putAll(key, new HashMap<String, Object>() {{
            put("open", false);
            put("currentPatient", "none");   // Current patient ID
            put("currentOrder", "none");     // Current consultation order number
            put("currentHandle", false);     // Is the current consultation being handled
            put("currentStart", "none");     // Start time
            put("currentEnd", "none");       // End time
            put("currentPayment", false);    // Has the current consultation been paid
            put("currentStatus", 1);         // 1: Not started, 2: In consultation, 3: Finished
            put("currentNotify", false);     // Not yet pushed to the doctor's end
            put("nextPatient", "none");      // Next waiting patient ID
            put("nextOrder", "none");        // Order number for the next waiting consultation
            put("nextStart", "none");        // Start time for the next waiting consultation
            put("nextEnd", "none");          // End time for the next waiting consultation
            put("nextPayment", false);       // Has the waiting consultation been paid
            put("nextNotify", false);        // Not yet pushed to the doctor's end
        }});

    }

    private int searchDoctorId(int userId) {
        HashMap<String, Object> map = misUserDao.searchRefId(userId);
        String job = MapUtil.getStr(map, "job");

        if (!"Doctor".equals(job)) {
            throw new HospitalException("The current user is not a doctor.");
        }

        Integer refId = MapUtil.getInt(map, "refId");
        if (refId == null) {
            throw new HospitalException("The current user is not associated with the doctor's table.");
        }
        return refId;
    }

    @Override
    public boolean offline(int userId) {
        // Find the primary key value of the doctor
        int doctorId = this.searchDoctorId(userId);
        String key = "online_doctor_" + doctorId;
        // Check if the online cache exists
        if (!redisTemplate.hasKey(key)) {
            return true;
        }

        // Check if there are ongoing consultations or waiting consultations
        List<String> list = redisTemplate.opsForHash().multiGet(key, new ArrayList<>() {{
            add("currentOrder");
            add("nextOrder");
        }});
        String currentOrder = list.get(0);
        String nextOrder = list.get(1);

        // If there is a current consultation or a waiting consultation, the doctor cannot go offline
        if (!"none".equals(currentOrder) || !"none".equals(nextOrder)) {
            return false;
        }

        // Delete the doctor's consultation cache
        redisTemplate.delete(key);
        return true;
    }

    @Override
    public void updateOpenFlag(int userId, boolean open) {
        // Find the primary key value of the doctor
        int doctorId = this.searchDoctorId(userId);
        String key = "online_doctor_" + doctorId;
        // If the online cache does not exist, it means the doctor is not online, so there's no need to update the "open" attribute value
        if (!redisTemplate.hasKey(key)) {
            return;
        }
        // Update the "open" attribute value, either opening or closing registration
        redisTemplate.opsForHash().put(key, "open", open);
    }

}
