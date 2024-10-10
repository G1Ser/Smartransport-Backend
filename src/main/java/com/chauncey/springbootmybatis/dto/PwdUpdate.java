package com.chauncey.springbootmybatis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PwdUpdate {
    @NotNull
    private String old_password;
    @NotNull
    @Pattern(regexp = "^\\S{5,16}$")
    private String new_password;
    @NotNull
    @Pattern(regexp = "^\\S{5,16}$")
    private String re_password;
}
