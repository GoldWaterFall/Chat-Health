package com.example.hospital.api.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Data
public class UpdateDoctorForm {
    @NotNull(message = "id cannot be null")
    @Min(value = 1, message = "id cannot be less than 1")
    private Integer id;

    @NotBlank(message = "name cannot be empty")
    @Pattern(regexp = "^[\\u4e00-\\u9f5a]{2,20}$", message = "name is incorrect")
    private String name;

    @NotBlank(message = "pid cannot be empty")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", message = "pid is incorrect")
    private String pid;

    @NotBlank(message = "sex cannot be empty")
    @Pattern(regexp = "^Man$|^Woman$", message = "sex is incorrect")
    private String sex;

    @NotBlank(message = "birthday cannot be empty")
    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$", message = "birthday is incorrect")
    private String birthday;

    @NotBlank(message = "school cannot be empty")
    @Length(min = 2, max = 50, message = "school is incorrect")
    private String school;

    @NotBlank(message = "degree cannot be empty")
    @Pattern(regexp = "^Undergraduate$|^Postgraduate$|^PHD$", message = "degree is incorrect")
    private String degree;

    @NotBlank(message = "tel cannot be empty")
    @Pattern(regexp = "^1[1-9][0-9]{9}$", message = "tel is incorrect")
    private String tel;

    @NotBlank(message = "address cannot be empty")
    @Length(max = 200, message = "address is incorrect")
    private String address;

    @NotBlank(message = "email cannot be empty")
    @Email
    @Length(max = 200, message = "email is incorrect")
    private String email;

    @NotBlank(message = "job cannot be empty")
    @Pattern(regexp = "^Attending Physician$|^Associate Attending Physician$|^Chief Physician$|^Deputy Chief Physician$", message = "job is incorrect")
    private String job;

    @NotBlank(message = "remark cannot be empty")
    @Length(max = 50, message = "remark is incorrect")
    private String remark;

    @NotBlank(message = "description cannot be empty")
    private String description;

    @NotBlank(message = "hiredate cannot be empty")
    @Pattern(regexp = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$", message = "hiredate is incorrect")
    private String hiredate;

    private String[] tag;

    @NotNull(message = "recommended cannot be null")
    private Boolean recommended;

    @NotNull(message = "status cannot be null")
    @Range(min = 1, max = 3, message = "status cannot be null")
    private Byte status;

    @NotNull(message = "subId cannot be null")
    @Min(value = 1, message = "subId cannot be less than 1")
    private Integer subId;
}
