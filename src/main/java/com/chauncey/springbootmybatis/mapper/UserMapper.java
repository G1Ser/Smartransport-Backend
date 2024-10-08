package com.chauncey.springbootmybatis.mapper;

import com.chauncey.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findById(Integer id);
}
