package com.chauncey.springbootmybatis.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
    String uploadFileToServer(MultipartFile file, String folderName, String username) throws JSchException, SftpException, IOException;
}
