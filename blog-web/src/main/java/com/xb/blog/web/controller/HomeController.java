package com.xb.blog.web.controller;

import com.xb.blog.common.constants.Result;
import com.xb.blog.common.pojo.BlogDocument;
import com.xb.blog.web.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/list/{page}")
    public Result list(@PathVariable("page") Long page) {
        List<BlogDocument> vos = blogService.listBlog(page);
        return Result.success(vos);
    }
}
