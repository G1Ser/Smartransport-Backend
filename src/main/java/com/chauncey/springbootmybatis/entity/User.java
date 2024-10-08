package com.chauncey.springbootmybatis.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户信息")
public class User {
    @Schema(description = "ID")
    private Integer id;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "年龄")
    private Short age;
    @Schema(description = "性别 0-male 1-female")
    private Short gender;
    @Schema(description = "电话")
    private String phone;

    public Integer getId() {
        return id;
    }

    public void SetId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void SetUsername(String username) {
        this.username = username;
    }

    public Short getAge() {
        return age;
    }

    public void SetAge(Short age) {
        this.age = age;
    }

    public Short getGender() {
        return gender;
    }

    public void SetId(Short gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void SetPhone(String phone) {
        this.phone = phone;
    }
}
