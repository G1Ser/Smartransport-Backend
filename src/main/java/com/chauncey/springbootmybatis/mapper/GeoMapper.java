package com.chauncey.springbootmybatis.mapper;

import com.chauncey.springbootmybatis.entity.Camera;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GeoMapper {
    //根据用户名查询用户
    List<Camera> getCameraData();

}
