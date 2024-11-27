package com.chauncey.springbootmybatis.controller;

import com.chauncey.springbootmybatis.entity.Result;
import com.chauncey.springbootmybatis.entity.User;
import com.chauncey.springbootmybatis.service.FileUploadService;
import com.chauncey.springbootmybatis.service.UserService;
import com.chauncey.springbootmybatis.utils.ThreadLocalUtils;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@RestController
@RequestMapping("/files")
@Tag(name = "文件管理")
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private UserService userService;
    @PostMapping("/uploadFile")
    @Operation(summary = "上传文件")
    public Result uploadFile(
            @RequestParam @Parameter(description = "文件夹 user_avatar-用户头像文件夹") String folderName,
            @RequestParam @Parameter(description = "本地文件") MultipartFile file
            ) throws JSchException, SftpException, IOException {
        Map<String, Object> map = ThreadLocalUtils.get();
        String username = (String) map.get("username");
        String url = fileUploadService.uploadFileToServer(file,folderName,username);
        if(url.startsWith("error:")){return Result.error(url);};
        return Result.success(url);
    }
    @PostMapping("/removeFolder")
    @Operation(summary = "删除文件夹")
    public Result removeFolder(
            @RequestParam @Parameter(description = "文件夹 user_avatar-用户头像文件夹") String folderName,
            @RequestParam @Parameter(description = "用户名") String username) throws JSchException, SftpException {
        User user = userService.findByUserName(username);
        if(user == null){
            return Result.error("用户不存在");
        }
        fileUploadService.removeFolderFromServer(folderName,username);
        return Result.success();
    }
}
