package com.chauncey.springbootmybatis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PwdForget {
    @NotNull
    @Pattern(regexp = "^1[3456789][0-9]{9}$", message = "手机号码格式不正确")
    private String phone;
    @NotNull
    @Pattern(regexp = "^\\S{5,16}$")
    private String new_password;
    @NotNull
    @Pattern(regexp = "^\\S{5,16}$")
    private String re_password;
}
