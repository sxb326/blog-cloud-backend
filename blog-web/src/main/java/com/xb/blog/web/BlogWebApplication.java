package com.xb.blog.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableDiscoveryClient//开启服务的注册发现
@SpringBootApplication
public class BlogWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogWebApplication.class, args);
    }

}
