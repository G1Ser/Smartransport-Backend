package com.chauncey.springbootmybatis.controller;

import com.chauncey.springbootmybatis.entity.Result;
import com.chauncey.springbootmybatis.service.FileUploadService;
import com.chauncey.springbootmybatis.utils.ThreadLocalUtils;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@RestController
@RequestMapping("/files")
@Tag(name = "文件管理")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;
    @PostMapping("/uploadFile")
    @Operation(summary = "上传文件")
    public Result uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folderName") String folderName
            ) throws JSchException, SftpException, IOException {
        Map<String, Object> map = ThreadLocalUtils.get();
        String username = (String) map.get("username");
        String url = fileUploadService.uploadFileToServer(file,folderName,username);
        if(url.startsWith("error:")){return Result.error(url);};
        return Result.success(url);
    }
}
