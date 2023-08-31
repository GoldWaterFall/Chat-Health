package com.example.hospital.api.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class LoginForm {
    @NotBlank(message = "username can not be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,50}$",message="username is not correct")
    private String username;

    @NotBlank(message = "password can not be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,50}$",message="password is not correct")
    private String password;
}
