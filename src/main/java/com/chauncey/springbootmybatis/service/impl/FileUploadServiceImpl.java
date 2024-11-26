package com.chauncey.springbootmybatis.service.impl;

import com.chauncey.springbootmybatis.service.FileUploadService;
import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${cloud.server.host}")
    private String remoteHost;
    @Value("${cloud.server.port}")
    private int remotePort;
    @Value("${cloud.server.username}")
    private String remoteUsername;
    @Value("${cloud.server.password}")
    private String remotePassword;
    @Value("${cloud.server.remoteDir}")
    private String remoteDir;
    @Override
    public String uploadFileToServer(MultipartFile file, String folderName, String username) throws JSchException, SftpException, IOException {
        try{
            //使用JSch连接远程服务器
            JSch jsch = new JSch();
            Session session = jsch.getSession(remoteUsername,remoteHost,remotePort);
            session.setPassword(remotePassword);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            //远程目录路径
            String remoteFolderPath = Paths.get(remoteDir, folderName).toString();
            createRemoteFolder(session,remoteFolderPath);
            String remoteFileName =  uploadFileToRemote(session,file,remoteFolderPath);
            String url = Paths.get(remoteFolderPath,remoteFileName).toString();
            return url;
        }catch(JSchException | SftpException | IOException e){
            return "error:" + e.getMessage();
        }
    }
    private void createRemoteFolder(Session session, String remoteFolderPath) throws JSchException, SftpException {
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        try {
            channelSftp.cd(remoteFolderPath);
        } catch (SftpException e) {
            // 如果文件夹不存在，创建文件夹
            channelSftp.mkdir(remoteFolderPath);
        }
        channelSftp.disconnect();
    }
    private String uploadFileToRemote(Session session,MultipartFile file,String remoteFolderPath) throws JSchException, SftpException, IOException {
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        channelSftp.cd(remoteFolderPath);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String originalFileName = file.getOriginalFilename();
        String remoteFileName = timestamp + "_" + originalFileName;
        channelSftp.put(file.getInputStream(),remoteFileName);
        channelSftp.disconnect();
        return remoteFileName;
    }
}
