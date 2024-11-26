package com.chauncey.springbootmybatis.service.impl;

import com.chauncey.springbootmybatis.service.FileUploadService;
import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Vector;

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
            Session session = connectToServer();
            //远程目录路径
            String remoteFolderPath = remoteDir + "/" + folderName + "/" + username;
            createRemoteFolder(session,remoteFolderPath);
            String remoteFileName =  uploadFileToRemote(session,file,remoteFolderPath);
            String url = remoteFolderPath + "/" + remoteFileName;
            return url;
        }catch(JSchException | SftpException | IOException e){
            return "error:" + e.getMessage();
        }
    }

    @Override
    public void removeFolderFromServer(String folderName, String username) throws JSchException, SftpException {
        //使用JSch连接远程服务器
        Session session = connectToServer();
        //远程目录路径
        String remoteFolderPath = remoteDir + "/" + folderName + "/" + username;
        deleteRemoteFolder(session,remoteFolderPath);
    }
    private Session connectToServer() throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(remoteUsername,remoteHost,remotePort);
        session.setPassword(remotePassword);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
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
    private void deleteRemoteFolder(Session session, String remoteFolderPath) throws JSchException, SftpException {
        ChannelSftp channelSftp  = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        if(isFolderExists(channelSftp,remoteFolderPath)){
            deleteFolderContents(channelSftp, remoteFolderPath);
            channelSftp.rmdir(remoteFolderPath);
        }
        channelSftp.disconnect();
    }

    //递归删除文件夹所有文件和子目录
    private void deleteFolderContents(ChannelSftp channelSftp,String remoteFolderPath) throws SftpException {
        // 获取文件夹内容
        Vector<ChannelSftp.LsEntry> files = channelSftp.ls(remoteFolderPath);
        for(ChannelSftp.LsEntry entry : files){
            String entryName = entry.getFilename();
            if (".".equals(entryName) || "..".equals(entryName)) {
                continue;  // 跳过当前目录和上一级目录
            }
            String filePath = remoteFolderPath + "/" + entryName;
            if(entry.getAttrs().isDir()){
                deleteFolderContents(channelSftp,filePath);
                channelSftp.rmdir(filePath);
            }else{
                channelSftp.rm(filePath);
            }
        }
    }
    private boolean isFolderExists(ChannelSftp channelSftp,String remoteFolderPath){
        try{
            channelSftp.cd(remoteFolderPath);
            return true;
        } catch (SftpException e) {
            return false;
        }
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
