package com.chauncey.springbootmybatis.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {
    // 将密码进行MD5加密
    public static String md5Password(String rawPassword){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(rawPassword.getBytes());
            StringBuilder hexString = new StringBuilder();
            for(byte b:messageDigest){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("MD5 encryption failed",e);
        }
    }
}
