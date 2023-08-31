package com.example.hospital.api.service.impl;

import com.example.hospital.api.common.PageUtils;
import com.example.hospital.api.db.dao.DoctorDao;
import com.example.hospital.api.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    @Resource
    private DoctorDao doctorDao;

    @Override
    public PageUtils searchByPage(Map param) {
        return null;
    }


}
