package com.chauncey.springbootmybatis.entity;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private Integer authority;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDelete;
}
