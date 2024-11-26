package com.chauncey.springbootmybatis.service.impl;

import com.chauncey.springbootmybatis.dto.UserList;
import com.chauncey.springbootmybatis.entity.PageBean;
import com.chauncey.springbootmybatis.entity.User;
import com.chauncey.springbootmybatis.mapper.AdminMapper;
import com.chauncey.springbootmybatis.service.AdminService;
import com.chauncey.springbootmybatis.utils.PasswordUtils;
import com.chauncey.springbootmybatis.utils.ThreadLocalUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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


    @Override
    public void resetUserPassword(String username, String re_pwd) {
        String encodePassword = PasswordUtils.md5Password(re_pwd);
        adminMapper.resetUserPassword(username,encodePassword);
    }

    @Override
    public void deleteUser(User user) {
        String username = user.getUsername();
        String nickname = user.getNickname();
        String phone = user.getPhone();
        String password = user.getPassword();
        String avatar = user.getAvatar();
        Integer authority = user.getAuthority();
        //删除users表里面对应的数据
        adminMapper.deleteUser(username);
        Map<String, Object> map = ThreadLocalUtils.get();
        String operator = (String) map.get("username");
        //增加到users log表里面对应的数据
        adminMapper.addDeleteLog(username,nickname,phone,password,authority,operator);
    }

    @Override
    public void changeUserAuthority(String username,Integer authority) {
        adminMapper.changeUserAuthority(username,authority);
    }
}
