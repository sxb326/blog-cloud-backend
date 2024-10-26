package com.xb.blog.common.core.config;

import com.xb.blog.common.core.aspect.InternalApiAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {
    @Bean
    public InternalApiAspect internalApiAspect() {
        return new InternalApiAspect();
    }
}