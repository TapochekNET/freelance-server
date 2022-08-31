package com.example.freelance.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Value("${upload.path}")
    private String uploadpath;
    @Value("${download.path}")
    private String downloadpath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/*").addResourceLocations("file:///"+uploadpath+"/");
        registry.addResourceHandler("/download/**").addResourceLocations("file:///"+downloadpath+"/");
    }
}
