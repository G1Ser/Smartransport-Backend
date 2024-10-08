package com.chauncey.springbootmybatis.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("智慧交通接口文档")
                        .description("智慧交通Knife4j的接口文档示例")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Chauncey")
                                .email("chauncey1129@163.com")));
    }
}
