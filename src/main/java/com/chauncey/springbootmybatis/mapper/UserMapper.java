package com.chauncey.springbootmybatis.mapper;

import com.chauncey.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    User findByUserName(String username);

    //添加新用户
    void addUser(String username, String encodePassword, String phone);

    void updatePwd(String username, String encodePassword);

    User findByPhone(String phone);

    // 更新用户信息
    void updateUserInfo(@Param("username") String username, @Param("nickname") String nickname, @Param("avatarUrl") String avatarUrl);
}
