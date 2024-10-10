package com.chauncey.springbootmybatis.controller;

import com.chauncey.springbootmybatis.dto.UserList;
import com.chauncey.springbootmybatis.entity.PageBean;
import com.chauncey.springbootmybatis.entity.Result;
import com.chauncey.springbootmybatis.entity.User;
import com.chauncey.springbootmybatis.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "管理员操作")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/getUserList")
    @Operation(summary = "获取用户列表")
    public Result<PageBean<User>> getUserList(@RequestBody @Validated UserList userList) {
        PageBean<User> user = adminService.getUserList(userList);
        return Result.success(user);
    }
}
