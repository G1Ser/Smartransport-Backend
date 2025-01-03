package com.chauncey.springbootmybatis.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Camera {
    @Schema(description = "ID")
    private Long id;
    @Schema(description="摄像头code")
    private String code;
    @Schema(description="摄像头位置")
    private String location;
    @Schema(description="视频流地址")
    private String url;
    @Schema(description="空间位置")
    private String geometry;
}
