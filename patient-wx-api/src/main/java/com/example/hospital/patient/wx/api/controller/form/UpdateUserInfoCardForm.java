package com.example.hospital.patient.wx.api.controller.form;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UpdateUserInfoCardForm {
    @NotNull(message = "id can not be null")
    @Min(value = 1, message = "id can not less than1")
    private Integer id;

    @NotBlank(message = "name can not be null")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,15}$", message = "name is not correct")
    private String name;

    @NotBlank(message = "sex can not be null")
    @Pattern(regexp = "^Man$|^Woman$", message = "sex is not correct")
    private String sex;

    @NotBlank(message = "pid can not be null")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", message = "pid内容不正确")
    private String pid;

    @NotBlank(message = "tel can not be null")
    @Pattern(regexp = "^1[0-9]{10}$", message = "tel is not correct")
    private String tel;

    @NotBlank(message = "birthday can not be null")
    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$",
            message = "birthday is not correct")
    private String birthday;

    @NotEmpty(message = "medicalHistory can not be null")
    private String[] medicalHistory;

    @NotBlank(message = "insuranceType can not be null")
    private String insuranceType;
}
