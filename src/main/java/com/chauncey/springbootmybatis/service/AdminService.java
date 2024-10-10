package com.chauncey.springbootmybatis.service;

import com.chauncey.springbootmybatis.dto.UserList;
import com.chauncey.springbootmybatis.entity.PageBean;
import com.chauncey.springbootmybatis.entity.User;

public interface AdminService {
    //条件列表分页查询
    PageBean<User> getUserList(UserList userList);
}
