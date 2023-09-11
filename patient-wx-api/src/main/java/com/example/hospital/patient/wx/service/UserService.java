package com.example.hospital.patient.wx.service;

import java.util.HashMap;

public interface UserService {
    public HashMap loginOrRegister(String code, String nickname, String photo, String sex);

    public HashMap searchUserInfo(int userId);
}
