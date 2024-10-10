package com.chauncey.springbootmybatis.service.impl;

import com.chauncey.springbootmybatis.dto.UserUpdate;
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
    public void register(String username, String password) {
        // 密码加密
        String encodePassword = PasswordUtils.md5Password(password);
        // 添加
        userMapper.addUser(username, encodePassword);
    }

    @Override
    public void update(UserUpdate userUpdate) {
        userMapper.update(userUpdate);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtils.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(Long id, String new_pwd) {
        String encodePassword = PasswordUtils.md5Password(new_pwd);
        userMapper.updatePwd(id, encodePassword);
    }
}
