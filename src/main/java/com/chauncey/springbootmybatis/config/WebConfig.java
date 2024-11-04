package com.chauncey.springbootmybatis.config;

import com.chauncey.springbootmybatis.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册接口和登录接口不拦截
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/user/register", "/user/login", "/user/forgetPwd")
                .excludePathPatterns("/doc.html/**", "/swagger-resources/**", "/webjars/**", "/v3/**");
    }
}
