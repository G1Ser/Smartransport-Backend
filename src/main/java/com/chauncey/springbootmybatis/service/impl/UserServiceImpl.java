package com.chauncey.springbootmybatis.service.impl;

import com.chauncey.springbootmybatis.entity.User;
import com.chauncey.springbootmybatis.mapper.UserMapper;
import com.chauncey.springbootmybatis.service.UserService;
import com.chauncey.springbootmybatis.utils.PasswordUtils;
import com.chauncey.springbootmybatis.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password, String phone) {
        // 密码加密
        String encodePassword = PasswordUtils.md5Password(password);
        // 添加
        userMapper.addUser(username, encodePassword, phone);
    }

    @Override
    public void updateUserInfo(Map<String, Object> updates) {
        Map<String, Object> map = ThreadLocalUtils.get();
        String username = (String) map.get("username");
        String nickname = updates.containsKey("nickname") ? (String) updates.get("nickname") : null;
        String avatarUrl = updates.containsKey("avatarUrl") ? (String) updates.get("avatarUrl") : null;
        userMapper.updateUserInfo(username, nickname, avatarUrl);
    }

    @Override
    public void updatePwd(String username, String new_pwd) {
        String encodePassword = PasswordUtils.md5Password(new_pwd);
        userMapper.updatePwd(username, encodePassword);
    }

    @Override
    public User findByPhone(String phone) {
        User u = userMapper.findByPhone(phone);
        return u;
    }
}
