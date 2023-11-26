package com.example.oracledemo.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class WebMvcConfig  extends WebMvcConfigurationSupport {
    public static final String PROJECT_PATH =System.getProperty("user.dir");
    public static final String LOCAL_BOOK_PIC_PATH = "file:"+PROJECT_PATH+"/image/book/";

    // 开启静态资源资源映射
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/book/**").addResourceLocations(LOCAL_BOOK_PIC_PATH);
    }


}
