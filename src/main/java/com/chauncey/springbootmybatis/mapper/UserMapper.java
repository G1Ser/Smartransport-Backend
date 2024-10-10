package com.chauncey.springbootmybatis.mapper;

import com.chauncey.springbootmybatis.dto.UserUpdate;
import com.chauncey.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    User findByUserName(String username);

    //添加新用户
    void addUser(String username, String encodePassword);

    void update(UserUpdate user);

    void updateAvatar(String avatarUrl, Integer id);

    void updatePwd(Long id, String encodePassword);
}
