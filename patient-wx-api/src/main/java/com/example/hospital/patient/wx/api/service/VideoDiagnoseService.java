package com.example.hospital.patient.wx.api.service;

import com.example.hospital.patient.wx.api.db.pojo.VideoDiagnoseEntity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Shiqi
 */
public interface VideoDiagnoseService {
    public ArrayList<HashMap> searchOnlineDoctorList(String subName, String job);
    public HashMap createVideoDiagnose(int userId, VideoDiagnoseEntity entity);
}
