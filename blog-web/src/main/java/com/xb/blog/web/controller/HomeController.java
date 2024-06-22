package com.xb.blog.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.blog.common.constants.Result;
import com.xb.blog.web.entity.BlogEntity;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.vo.BlogListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/list")
    public Result list() {
        List<BlogListVo> vos = blogService.listBlog();
        return Result.success(vos);
    }
}
