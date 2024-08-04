package com.xb.blog.web.controller;

import com.xb.blog.common.constants.Result;
import com.xb.blog.common.pojo.BlogDocument;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.vo.BlogListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/list")
    public Result list(@RequestParam("page") Long page, @RequestParam("orderType") String orderType) {
        List<BlogListVo> vos = blogService.listBlog(page, orderType);
        return Result.success(vos);
    }
}
