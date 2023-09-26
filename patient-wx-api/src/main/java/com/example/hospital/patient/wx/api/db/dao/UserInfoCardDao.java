package com.example.hospital.patient.wx.api.db.dao;

import com.example.hospital.patient.wx.api.db.pojo.UserInfoCardEntity;

import java.util.HashMap;
import java.util.Map;

public interface UserInfoCardDao {
    public String searchUserTel(int userId);
    public Integer hasUserInfoCard(int userId);

    public void insert(UserInfoCardEntity entity);

    public HashMap searchUserInfoCard(int userId);
    public int update(UserInfoCardEntity entity);
}
