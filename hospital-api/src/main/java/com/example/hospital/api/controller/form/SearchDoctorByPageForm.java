package com.example.hospital.api.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SearchDoctorByPageForm {
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,20}$", message = "name content is incorrect")
    private String name;

    @Min(value = 1, message = "deptId can not less than 1")
    private Integer deptId;

    @Pattern(regexp = "^Undergraduate$|^Postgraduate$|^PHD$", message = "degree content is incorrect")
    private String degree;

    @Pattern(regexp = "^Attending Physician$|^Associate Attending Physician$|^Chief Physician$|^Deputy Chief Physician$", message = "job content is incorrect")
    private String job;

    private Boolean recommended;

    @NotNull(message = "status can not be null")
    @Range(min = 1, max = 3, message = "status content is incorrect")
    private Byte status;

    @Pattern(regexp = "^ASC$|^DESC$", message = "order content is incorrect")
    private String order;

    @NotNull(message = "page content is incorrect")
    @Min(value = 1, message = "page can not less than 1")
    private Integer page;

    @NotNull(message = "length can not be null")
    @Range(min = 10, max = 50, message = "length content is incorrect")
    private Integer length;
}

