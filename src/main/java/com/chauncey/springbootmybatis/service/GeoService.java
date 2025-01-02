package com.chauncey.springbootmybatis.service;

import com.chauncey.springbootmybatis.entity.Camera;

import java.util.List;

public interface GeoService {
    //根据用户名查询用户
    List<Camera> getCameraData();
}
