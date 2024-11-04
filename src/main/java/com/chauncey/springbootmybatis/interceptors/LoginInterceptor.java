package com.chauncey.springbootmybatis.interceptors;

import com.chauncey.springbootmybatis.entity.Result;
import com.chauncey.springbootmybatis.utils.JwtUtils;
import com.chauncey.springbootmybatis.utils.ThreadLocalUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //令牌验证
       String token = request.getHeader("Authorization");
       //验证token
        try{
            Map<String, Object> claims = JwtUtils.parseToken(token);
            //将业务数据存储到ThreadLocal中
            ThreadLocalUtils.set(claims);
            return true;
        }catch (Exception e){
            // 设置响应内容类型为 JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 创建失败的结果
            Result<?> result = Result.error(401);

            // 写入响应内容
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtils.remove();
    }
}
