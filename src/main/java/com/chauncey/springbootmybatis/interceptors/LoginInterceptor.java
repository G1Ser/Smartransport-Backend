package com.chauncey.springbootmybatis.interceptors;

import com.chauncey.springbootmybatis.utils.JwtUtils;
import com.chauncey.springbootmybatis.utils.ThreadLocalUtils;
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
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal中的数据
        ThreadLocalUtils.remove();
    }
}
