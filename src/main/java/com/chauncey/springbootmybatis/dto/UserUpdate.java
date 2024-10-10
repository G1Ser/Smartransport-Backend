package com.chauncey.springbootmybatis.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdate {
    @NotNull
    private Long id;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
}
