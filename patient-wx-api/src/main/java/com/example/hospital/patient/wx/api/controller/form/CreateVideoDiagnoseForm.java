package com.example.hospital.patient.wx.api.controller.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateVideoDiagnoseForm {
    @NotNull(message = "doctorId can not be null")
    @Min(value = 1, message = "doctorId can not less than 1")
    private Integer doctorId;

}

