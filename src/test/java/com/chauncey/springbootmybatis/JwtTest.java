package com.chauncey.springbootmybatis;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        //生成jwt代码
        String token = JWT.create()
                .withClaim("user",claims)
                //过期时间
                .withExpiresAt(new Date(System.currentTimeMillis()))
                //指定算法，配置密钥
                .sign(Algorithm.HMAC256("chauncey"));
        System.out.println(token);
    }
    @Test
    public void testParse(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3Mjg1MzgxNjl9" +
                ".cUBkAKhkoGU_mqNPUVzxWA7YNmxUkxlGy57UHpelSOM";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("chauncey")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
}
