package com.example.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class AppWebConfig implements WebMvcConfigurer {

    @Resource
    private ApiPathProperties apiPathProperties;

    public void configurePathMatch(PathMatchConfigurer configurer){
        configurer
                .addPathPrefix(apiPathProperties.getGlobalPrefix(),
                        c -> c.isAnnotationPresent(ApiRestController.class));
    }
}
