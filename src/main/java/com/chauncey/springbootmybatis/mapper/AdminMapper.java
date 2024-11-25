package com.chauncey.springbootmybatis.mapper;

import com.chauncey.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<User> getUserList(String username, Integer authority);

    void resetUserPassword(String username, String encodePassword);

    void deleteUser(String username);


    void changeUserAuthority(String username, Integer authority);

    void addDeleteLog(String username, String nickname, String phone, String password, String avatar, Integer authority, String operator);
}
