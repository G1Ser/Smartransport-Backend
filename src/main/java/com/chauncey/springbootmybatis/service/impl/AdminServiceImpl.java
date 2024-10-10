package com.chauncey.springbootmybatis.service.impl;

import com.chauncey.springbootmybatis.dto.UserList;
import com.chauncey.springbootmybatis.entity.PageBean;
import com.chauncey.springbootmybatis.entity.User;
import com.chauncey.springbootmybatis.mapper.AdminMapper;
import com.chauncey.springbootmybatis.service.AdminService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public PageBean<User> getUserList(UserList userList) {
        PageBean<User> user = new PageBean<>();
        Integer pageNum = userList.getPageNum();
        Integer pageSize = userList.getPageSize();
        Integer authority = userList.getAuthority();
        String username = userList.getUsername();
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<User> ul = adminMapper.getUserList(username,authority);
        Page<User> p = (Page<User>) ul;
        //填充到PageBean对象
        user.setTotal(p.getTotal());
        user.setUsers(p.getResult());
        return user;
    }
}
