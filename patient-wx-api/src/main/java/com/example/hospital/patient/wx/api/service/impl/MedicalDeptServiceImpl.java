package com.example.hospital.patient.wx.api.service.impl;

import com.example.hospital.patient.wx.api.db.dao.MedicalDeptDao;
import com.example.hospital.patient.wx.api.service.MedicalDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class MedicalDeptServiceImpl implements MedicalDeptService {
    @Resource
    private MedicalDeptDao medicalDeptDao;

    @Override
    public ArrayList<HashMap> searchMedicalDeptList(Map param) {
        ArrayList<HashMap> list = medicalDeptDao.searchMedicalDeptList(param);
        return list;
    }
}
