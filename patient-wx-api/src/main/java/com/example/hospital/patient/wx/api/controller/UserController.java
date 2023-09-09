package com.example.hospital.patient.wx.api.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.example.hospital.patient.wx.api.common.R;
import com.example.hospital.patient.wx.api.controller.form.LoginOrRegisterForm;
import com.example.hospital.patient.wx.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/loginOrRegister")
    public R loginOrRegister(@RequestBody @Valid LoginOrRegisterForm form) {
        HashMap map = userService.loginOrRegister(form.getCode(), form.getNickname(), form.getPhoto(), form.getSex());
        String msg = MapUtil.getStr(map, "msg");
        Integer id = MapUtil.getInt(map, "id");
        StpUtil.login(id);
        String token = StpUtil.getTokenValue();
        return R.ok(msg).put("token", token);
    }
}
