package com.chauncey.springbootmybatis.service;

import com.chauncey.springbootmybatis.entity.User;

import java.util.Map;

public interface UserService {
    //根据用户名查询用户
    User findByUserName(String username);
    //注册
    void register(String username, String password, String phone);

    //修改用户信息
    void updateUserInfo(Map<String, Object> updates);

    //修改用户密码
    void updatePwd(Long id, String new_pwd);

    User findByPhone(String phone);
}
