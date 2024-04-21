package com.xb.blog.gateway;

import com.xb.blog.gateway.constants.AuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableConfigurationProperties({AuthProperties.class})//开启配置文件读取
@EnableDiscoveryClient//开启服务的注册发现
@SpringBootApplication
public class XbBlogGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(XbBlogGatewayApplication.class, args);
    }
}
