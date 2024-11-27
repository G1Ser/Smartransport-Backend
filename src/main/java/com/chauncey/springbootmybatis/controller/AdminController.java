package com.chauncey.springbootmybatis.controller;

import com.chauncey.springbootmybatis.dto.UserList;
import com.chauncey.springbootmybatis.entity.PageBean;
import com.chauncey.springbootmybatis.entity.Result;
import com.chauncey.springbootmybatis.entity.User;
import com.chauncey.springbootmybatis.service.AdminService;
import com.chauncey.springbootmybatis.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "管理员操作")
@Validated
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    @PostMapping("/getUserList")
    @Operation(summary = "获取用户列表")
    public Result<PageBean<User>> getUserList(@RequestBody UserList userList) {
        PageBean<User> user = adminService.getUserList(userList);
        return Result.success(user);
    }

    @PatchMapping("/resetUserPassword")
    @Operation(summary = "重置用户密码")
    public Result resetUserPassword(@RequestParam @Parameter(description = "用户名") String username) {
        User user = userService.findByUserName(username);
        String re_pwd = "123456";
        if (user == null) {
            return Result.error("用户不存在");
        }
        adminService.resetUserPassword(username, re_pwd);
        return Result.success();
    }

    @DeleteMapping("/deleteUser/{username}")
    @Operation(summary = "删除用户")
    public Result deleteUser(@PathVariable @Parameter(description = "用户名") String username) {
        User user = userService.findByUserName(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        adminService.deleteUser(user);
        return Result.success();
    }

    @PatchMapping("/changeUserAuthority")
    @Operation(summary = "更改用户权限")
    public Result changeUserAuthority(@RequestParam @Parameter(description = "用户名") String username) {
        User user = userService.findByUserName(username);
        Integer authority = (Integer) user.getAuthority();
        if (user == null) {
            return Result.error("用户不存在");
        }
        Integer n_authority = authority == 1 ? 2 : 1;
        adminService.changeUserAuthority(username,n_authority);
        return Result.success();
    }
}
