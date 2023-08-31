package com.example.hospital.api.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.example.hospital.api.db.dao.MisUserDao;
import com.example.hospital.api.service.MisUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
/**
 * @author ShiqiDing
 */
@Service
public class MisUserServiceImpl implements MisUserService {
    @Resource
    private MisUserDao misUserDao;
    @Override
    public Integer login(Map param) {
        String username = MapUtil.getStr(param,"username");
        String password = MapUtil.getStr(param,"password");
        MD5  md5 = MD5.create();
        String temp=md5.digestHex(username);
        //first six characters
        String tempStart = StrUtil.subWithLength(temp,0,6);
        //last three characters
        String tempEnd = StrUtil.subSuf(temp,temp.length()-3);
        //Obfuscate original password and hash encryption
        password=md5.digestHex(tempStart+password+tempEnd);
        param.replace("password",password);
        Integer userId = misUserDao.login(param);
        return userId;

    }
}
