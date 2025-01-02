package com.chauncey.springbootmybatis.service.impl;

import com.chauncey.springbootmybatis.entity.Camera;
import com.chauncey.springbootmybatis.mapper.GeoMapper;
import com.chauncey.springbootmybatis.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeoServiceImpl implements GeoService {
    @Autowired
    private GeoMapper geoMapper;

    @Override
    public List<Camera> getCameraData() {
        List<Camera> cameras = geoMapper.getCameraData();
        return cameras;
    }
}
