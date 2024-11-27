package com.chauncey.springbootmybatis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserList {
    @NotNull
    @Schema(description = "页数")
    private Integer pageNum;
    @NotNull
    @Schema(description = "每页条数")
    private Integer pageSize;
    @Schema(description = "用户权限")
    private Integer authority;
    @Schema(description = "用户名")
    private String username;
}
