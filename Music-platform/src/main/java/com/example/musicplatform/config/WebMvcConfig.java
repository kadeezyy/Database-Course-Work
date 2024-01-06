package com.example.musicplatform.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:application.yml")
public class WebMvcConfig implements WebMvcConfigurer {
    private final String uploadPath;

    public WebMvcConfig(Environment environment) {
        this.uploadPath = environment.getRequiredProperty("upload.path");
    }


    //todo: add handling to not being able to download sources except 'storage' directory
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://" + uploadPath + "/img/");
        registry.addResourceHandler("/audio/**")
                .addResourceLocations("file://" + uploadPath + "/audio/");
    }
}
