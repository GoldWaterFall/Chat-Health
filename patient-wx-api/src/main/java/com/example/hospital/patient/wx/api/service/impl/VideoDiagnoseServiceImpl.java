package com.example.hospital.patient.wx.api.service.impl;

import cn.hutool.core.map.MapUtil;
import com.example.hospital.patient.wx.api.service.VideoDiagnoseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private RedisTemplate redisTemplate;

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
}

