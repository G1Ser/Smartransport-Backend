package com.chauncey.springbootmybatis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    @Schema(description = "ID")
    private Long id;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "昵称")
    private String nickname;
    @Schema(description = "用户密码")
    @JsonIgnore
    private String password;
    @Schema(description = "用户权限 0-admin 1-common user 2-traffic manager")
    private Integer authority;
    @Schema(description = "用户头像")
    private String avatar;
    @Schema(description = "创建时间")
    @JsonIgnore
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    @JsonIgnore
    private LocalDateTime updateTime;
    @Schema(description = "是否删除")
    @JsonIgnore
    private Integer isDelete;
}
