package com.xb.blog.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient//开启服务的注册发现
@ComponentScan(basePackages = "com.xb.blog")
@SpringBootApplication
public class BlogSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogSearchApplication.class, args);
    }

}
