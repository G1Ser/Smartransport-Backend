package com.chauncey.springbootmybatis.controller;

import com.chauncey.springbootmybatis.entity.User;
import com.chauncey.springbootmybatis.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name="用户列表")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/findById")
    @Operation(summary = "查询用户")
    public User findById(Integer id){
        return userService.findById(id);
    }
}
