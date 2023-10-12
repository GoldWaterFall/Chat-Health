package com.example.hospital.api.controller.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Shiqi
 */
@Data
public class UpdateCanRegisterForm {
    @NotNull(message = "open不能为空")
    private Boolean open;
}

