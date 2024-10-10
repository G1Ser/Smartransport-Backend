package com.chauncey.springbootmybatis.controller;

import com.chauncey.springbootmybatis.dto.PwdUpdate;
import com.chauncey.springbootmybatis.dto.UserUpdate;
import com.chauncey.springbootmybatis.entity.Result;
import com.chauncey.springbootmybatis.entity.User;
import com.chauncey.springbootmybatis.service.UserService;
import com.chauncey.springbootmybatis.utils.JwtUtils;
import com.chauncey.springbootmybatis.utils.PasswordUtils;
import com.chauncey.springbootmybatis.utils.ThreadLocalUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "用户列表")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result register(
            @Parameter(description = "用户名必须是5-16位非空字符", required = true)
            @RequestParam
            @Pattern(regexp = "^\\S{5,16}$") String username,
            @Parameter(description = "用户密码必须是5-16位非空字符", required = true)
            @RequestParam
            @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户
        User u = userService.findByUserName(username);
        if (u == null) {
            //注册
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户已存在");
        }
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<String> login(String username, String password) {
        User u = userService.findByUserName(username);
        if (u == null) {
            return Result.error("用户不存在");
        }
        //判断密码是否正确
        if (PasswordUtils.md5Password(password).equals(u.getPassword())) {
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("username", u.getUsername());
            String token = JwtUtils.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    @Operation(summary = "获取用户信息")
    public Result<User> getUserInfo() {
        Map<String, Object> map = ThreadLocalUtils.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/updateUserInfo")
    @Operation(summary = "更改用户信息")
    public Result updateUserInfo(@RequestBody @Validated UserUpdate userUpdate) {
        userService.update(userUpdate);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    @Operation(summary = "更改用户头像")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    @Operation(summary = "更改用户密码")
    public Result updatePwd(@RequestBody @Validated PwdUpdate pwdUpdate) {
        Map<String, Object> map = ThreadLocalUtils.get();
        String username = (String) map.get("username");
        String old_pwd = pwdUpdate.getOld_password();
        String new_pwd = pwdUpdate.getNew_password();
        String re_pwd = pwdUpdate.getRe_password();
        User user = userService.findByUserName(username);
        if (!user.getPassword().equals(PasswordUtils.md5Password(old_pwd))) {
            return Result.error("原密码错误");
        }
        if (!new_pwd.equals(re_pwd)) {
            return Result.error("两次密码输入不一致");
        }
        userService.updatePwd(user.getId(), new_pwd);
        return Result.success();
    }
}
