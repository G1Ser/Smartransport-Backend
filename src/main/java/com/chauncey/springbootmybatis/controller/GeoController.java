package com.chauncey.springbootmybatis.controller;
import com.chauncey.springbootmybatis.entity.Camera;
import com.chauncey.springbootmybatis.entity.Result;
import com.chauncey.springbootmybatis.service.GeoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geo")
@Validated
@Tag(name = "GIS空间数据服务")
public class GeoController {
    @Autowired
    private GeoService geoService;

    @GetMapping("/cameraData")
    @Operation(summary = "获取摄像头数据")
    public Result<List<Camera>> getCameraData() {
        List<Camera> cameras = geoService.getCameraData();
        return Result.success(cameras);
    }
}
