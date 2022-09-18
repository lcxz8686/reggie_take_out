package com.harmony.reggie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 配置发行静态资源
@Configuration
public class WebMVCConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");
        registry.addResourceHandler("/backend/**")
                .addResourceLocations("classpath:/backend/");
    }
}

// 实现WebMvcConfigurer接口
/*
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**")
                .addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");
    }
}
*/