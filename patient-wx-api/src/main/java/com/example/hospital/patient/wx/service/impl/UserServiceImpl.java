package com.example.hospital.patient.wx.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.hospital.patient.wx.api.db.dao.UserDao;
import com.example.hospital.patient.wx.api.db.dao.UserInfoCardDao;
import com.example.hospital.patient.wx.api.db.pojo.UserEntity;
import com.example.hospital.patient.wx.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Value("${wechat.app-id}")
    private String appId;

    @Value("${wechat.app-secret}")
    private String appSecret;

    @Resource
    private UserDao userDao;

    @Resource
    private UserInfoCardDao userInfoCardDao;

    @Override
    public HashMap loginOrRegister(String code, String nickname, String photo, String sex) {
        // Redeem open_id with temporary authorization
        String openId = this.getOpenId(code);

        HashMap map = new HashMap();
        // Is it a registered user
        Integer id = userDao.searchAlreadyRegistered(openId);
        if (id == null) {
            UserEntity entity = new UserEntity();
            entity.setOpenId(openId);
            entity.setNickname(nickname);
            entity.setPhoto(photo);
            entity.setSex(sex);
            entity.setStatus((byte) 1);
            //Perform new user registration
            userDao.insert(entity);
            //Query the primary key value of the new user
            id = userDao.searchAlreadyRegistered(entity.getOpenId());
            map.put("msg", "registration success");
        }
        else{
            map.put("msg", "Login success");
        }
        // Query the phone number in the patient information card
        String tel = userInfoCardDao.searchUserTel(id);
        map.put("id", id);
        map.put("tel",tel);
        return map;
    }

    // Get the open_id string of the patient’s WeChat
    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap map = new HashMap();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        String openId = json.getStr("openid");
        if (openId == null || openId.length() == 0) {
            throw new RuntimeException("Temporary login credentials error");
        }

        return openId;
    }
}

