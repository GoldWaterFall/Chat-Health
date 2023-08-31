package com.example.hospital.api.service;

import com.example.hospital.api.common.PageUtils;

import java.util.Map;

public interface DoctorService {
    public PageUtils searchByPage(Map param);
}
