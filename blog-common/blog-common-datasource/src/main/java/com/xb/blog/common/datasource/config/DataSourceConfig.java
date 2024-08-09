package com.xb.blog.common.datasource.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xb.blog.common.datasource.config.handler.CustomMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注入一些需要的bean
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new CustomMetaObjectHandler();
    }
}
