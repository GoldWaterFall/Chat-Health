package com.example.hospital.patient.wx.api.service;

import com.example.hospital.patient.wx.api.db.pojo.UserInfoCardEntity;

import java.util.HashMap;

public interface UserInfoCardService {
    public boolean hasUserInfoCard(int userId);
    public void insert(UserInfoCardEntity entity);

    public HashMap searchUserInfoCard(int userId);
    public void update(UserInfoCardEntity entity);
}
