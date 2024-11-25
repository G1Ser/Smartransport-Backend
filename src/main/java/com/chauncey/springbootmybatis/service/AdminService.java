package com.chauncey.springbootmybatis.service;

import com.chauncey.springbootmybatis.dto.UserList;
import com.chauncey.springbootmybatis.entity.PageBean;
import com.chauncey.springbootmybatis.entity.User;

public interface AdminService {
    //条件列表分页查询
    PageBean<User> getUserList(UserList userList);
    //根据id查询用户
    //重置用户密码
    void resetUserPassword(String username,String re_pwd);
    //删除用户
    void deleteUser(User user);

    void changeUserAuthority(String username,Integer authority);
}
