package com.chauncey.springbootmybatis.mapper;

import com.chauncey.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<User> getUserList(String username, Integer authority);
}
