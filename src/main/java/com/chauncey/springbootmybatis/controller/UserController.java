package com.chauncey.springbootmybatis.controller;

import com.chauncey.springbootmybatis.dto.PwdForget;
import com.chauncey.springbootmybatis.dto.PwdUpdate;
import com.chauncey.springbootmybatis.dto.RegisterParams;
import com.chauncey.springbootmybatis.entity.Result;
import com.chauncey.springbootmybatis.entity.User;
import com.chauncey.springbootmybatis.service.UserService;
import com.chauncey.springbootmybatis.utils.JwtUtils;
import com.chauncey.springbootmybatis.utils.PasswordUtils;
import com.chauncey.springbootmybatis.utils.ThreadLocalUtils;
import io.swagger.v3.oas.annotations.Operation;
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
    public Result register(@RequestBody @Validated RegisterParams params) {
        String username = params.getUsername();
        String password = params.getPassword();
        String phone = params.getPhone();
        //查询号码
        if(userService.findByPhone(phone) != null){
            return Result.error("手机号已被注册");
        }
        //查询用户
        if(userService.findByUserName(username) !=null){
            return Result.error("用户已存在");
        }
        //注册新用户
        userService.register(username,password,phone);
        return Result.success();
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
    public Result updateUserInfo(
            @RequestParam(required = false) @Pattern(regexp = "^\\S{1,10}$") String nickname,
            @RequestParam(required = false) @URL String avatarUrl) {
        Map<String, Object> updates = new HashMap<>();
        if(nickname ==null && avatarUrl == null){
            return Result.success();
        }
        //处理昵称更新
        if (nickname != null) {
            updates.put("nickname", nickname);
        }
        //处理头像更新
        if (avatarUrl != null) {
            updates.put("avatarUrl", avatarUrl);
        }
        userService.updateUserInfo(updates);
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
        userService.updatePwd(username, new_pwd);
        return Result.success();
    }

    @PatchMapping("/forgetPwd")
    @Operation(summary = "忘记密码")
    public Result forgetPwd(@RequestBody @Validated PwdForget pwdForget) {
        String phone = pwdForget.getPhone();
        String new_pwd = pwdForget.getNew_password();
        String re_pwd = pwdForget.getRe_password();
        User isPhone = userService.findByPhone(phone);
        //检查手机号是否已经注册
        if(isPhone == null){
            return Result.error("该手机号未注册");
        }
        //验证密码是否一致
        if(!new_pwd.equals(re_pwd)){
            return Result.error("两次密码输入不一致");
        }
        //更新密码
        String username = isPhone.getUsername();
        userService.updatePwd(username,new_pwd);
        return Result.success();
    }
}
