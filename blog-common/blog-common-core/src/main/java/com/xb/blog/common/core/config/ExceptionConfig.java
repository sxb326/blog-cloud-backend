package com.xb.blog.common.core.config;

import com.xb.blog.common.core.handler.CustomExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionConfig {
    @Bean
    public CustomExceptionHandler customExceptionHandler() {
        return new CustomExceptionHandler();
    }
}