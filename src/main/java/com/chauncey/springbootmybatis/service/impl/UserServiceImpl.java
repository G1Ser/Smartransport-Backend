package com.chauncey.springbootmybatis.service.impl;

import com.chauncey.springbootmybatis.entity.User;
import com.chauncey.springbootmybatis.mapper.UserMapper;
import com.chauncey.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u  = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //密码加密
    }
}
