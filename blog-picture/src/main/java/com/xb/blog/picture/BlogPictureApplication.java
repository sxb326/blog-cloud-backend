package com.xb.blog.picture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.xb.blog")
@SpringBootApplication
public class BlogPictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogPictureApplication.class, args);
    }

}
