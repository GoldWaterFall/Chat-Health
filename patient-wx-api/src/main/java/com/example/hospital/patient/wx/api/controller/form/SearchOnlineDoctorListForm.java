package com.example.hospital.patient.wx.api.controller.form;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author Shiqi
 */
@Data
public class SearchOnlineDoctorListForm {
    private String subName;

    @Pattern(regexp = "^Attending Physician$|^Associate Attending Physician$|^Chief Physician$|^Deputy Chief Physician$", message = "job is not correct")
    private String job;
}
