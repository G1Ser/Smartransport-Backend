package com.chauncey.springbootmybatis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserList {
    @NotNull
    private Integer pageNum;
    @NotNull
    private Integer pageSize;
    private Integer authority;
    private String username;
}
