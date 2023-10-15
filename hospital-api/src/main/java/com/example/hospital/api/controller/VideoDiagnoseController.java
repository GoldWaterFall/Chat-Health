package com.example.hospital.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import com.example.hospital.api.common.R;
import com.example.hospital.api.config.tencent.TrtcUtil;
import com.example.hospital.api.controller.form.UpdateCanRegisterForm;
import com.example.hospital.api.service.VideoDiagnoseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Shiqi
 */
@RestController
@RequestMapping("/video_diagnose")
@Slf4j
public class VideoDiagnoseController {
    @Value("${tencent.trtc.appId}")
    private int appId;

    @Resource
    private TrtcUtil trtcUtil;

    @GetMapping("/searchMyUserSig")
    @SaCheckLogin
    public R searchMyUserSig() {
        int userId = StpUtil.getLoginIdAsInt();
        String userSig = trtcUtil.genUserSig(userId + "");
        return R.ok().put("userSig", userSig).put("userId", userId).put("appId", appId);
    }

    @Resource
    private VideoDiagnoseService videoDiagnoseService;

    @GetMapping("/online")
    @SaCheckLogin
    @SaCheckPermission(value = {"VIDEO_DIAGNOSE:DIAGNOSE"}, mode = SaMode.OR)
    public R online() {
        int userId = StpUtil.getLoginIdAsInt();
        videoDiagnoseService.online(userId);
        return R.ok();
    }
    @GetMapping("/offline")
    @SaCheckLogin
    @SaCheckPermission(value = {"VIDEO_DIAGNOSE:DIAGNOSE"}, mode = SaMode.OR)
    public R offline() {
        int userId = StpUtil.getLoginIdAsInt();
        boolean bool = videoDiagnoseService.offline(userId);
        return R.ok().put("result", bool);
    }

    @PostMapping("/updateOpenFlag")
    @SaCheckLogin
    @SaCheckPermission(value = {"VIDEO_DIAGNOSE:DIAGNOSE"}, mode = SaMode.OR)
    public R updateOpenFlag(@RequestBody @Valid UpdateCanRegisterForm form) {
        int userId = StpUtil.getLoginIdAsInt();
        videoDiagnoseService.updateOpenFlag(userId, form.getOpen());
        return R.ok();
    }
}

