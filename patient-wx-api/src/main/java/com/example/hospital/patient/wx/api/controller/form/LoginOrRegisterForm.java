package com.example.hospital.patient.wx.api.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class LoginOrRegisterForm {
    @NotBlank(message = "code can not be null")
    private String code;

    @NotBlank(message = "nickname can not be null")
    private String nickname;

    @NotBlank(message = "photo can not be null")
    private String photo;

    @NotBlank(message = "sex can not be null")
    @Pattern(regexp = "^Man$|^Woman$",message = "sex is not correct")
    private String sex;

}

