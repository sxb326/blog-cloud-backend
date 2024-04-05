package com.xb.blog.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient//开启服务的注册发现
@SpringBootApplication
public class BlogGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogGatewayApplication.class, args);
    }
}
