package com.chauncey.springbootmybatis.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class RegisterParams {
    @NotNull
    @Schema(description = "用户名必须是5-16位非空字符")
    @Pattern(regexp = "^\\S{5,16}$", message = "用户名必须是5-16的非空字符")
    private String username;
    @NotNull
    @Schema(description = "用户密码必须是5-16位非空字符")
    @Pattern(regexp = "^\\S{5,16}$", message = "用户密码必须是5-16的非空字符")
    private String password;
    @NotNull
    @Schema(description = "正确的手机格式")
    @Pattern(regexp = "^1[3456789][0-9]{9}$", message = "手机号码格式不正确")
    private String phone;
}
