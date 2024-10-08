package com.chauncey.springbootmybatis.service;

import com.chauncey.springbootmybatis.entity.User;

public interface UserService {
    public User findById(Integer id);
}
