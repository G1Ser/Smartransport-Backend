package com.chauncey.springbootmybatis.service;

import com.chauncey.springbootmybatis.dto.UserUpdate;
import com.chauncey.springbootmybatis.entity.User;

public interface UserService {
    //根据用户名查询用户
    User findByUserName(String username);
    //注册
    void register(String username, String password);

    //修改用户昵称
    void update(UserUpdate userUpdate);
    //修改用户头像
    void updateAvatar(String avatarUrl);

    //修改用户密码
    void updatePwd(Long id, String new_pwd);
}
