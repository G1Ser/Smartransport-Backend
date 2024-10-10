package com.chauncey.springbootmybatis.mapper;

import com.chauncey.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<User> getUserList(String username, Integer authority);

    void resetUserPassword(Integer id, String encodePassword);

    void deleteUser(Integer id);

    User findById(Integer id);

    void changeUserAuthority(Integer id, Integer authority);
}
