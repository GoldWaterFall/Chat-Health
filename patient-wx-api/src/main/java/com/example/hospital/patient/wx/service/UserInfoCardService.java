package com.example.hospital.patient.wx.service;

import com.example.hospital.patient.wx.api.db.pojo.UserInfoCardEntity;

import java.util.HashMap;

public interface UserInfoCardService {
    public void insert(UserInfoCardEntity entity);

    public HashMap searchUserInfoCard(int userId);
    public void update(UserInfoCardEntity entity);
}
