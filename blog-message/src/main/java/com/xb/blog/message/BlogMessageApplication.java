package com.xb.blog.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient//开启服务的注册发现
@ComponentScan(basePackages = "com.xb.blog")
@SpringBootApplication
public class BlogMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogMessageApplication.class, args);
    }

}
