package com.chauncey.springbootmybatis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PwdUpdate {
    @NotNull
    @Schema(description = "用户旧密码")
    private String old_password;
    @NotNull
    @Pattern(regexp = "^\\S{5,16}$")
    @Schema(description = "新密码必须是5-16位非空字符")
    private String new_password;
    @NotNull
    @Pattern(regexp = "^\\S{5,16}$")
    @Schema(description = "确认密码")
    private String re_password;
}
