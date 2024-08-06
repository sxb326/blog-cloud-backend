package com.xb.blog.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.blog.common.constants.Result;
import com.xb.blog.web.entity.CategoryEntity;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.service.CategoryService;
import com.xb.blog.web.vo.BlogListVo;
import com.xb.blog.web.vo.BlogTopVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result list(@RequestParam("page") Long page, @RequestParam("categoryUid") String categoryUid, @RequestParam("orderType") String orderType) {
        List<BlogListVo> vos = blogService.listBlog(page, categoryUid, orderType);
        return Result.success(vos);
    }

    @GetMapping("/category")
    public Result category() {
        List<CategoryEntity> list = categoryService.list(new QueryWrapper<CategoryEntity>().orderByAsc("sort"));
        return Result.success(list);
    }

    @GetMapping("/top10")
    public Result top10() {
        List<BlogTopVo> list = blogService.getTop10List();
        return Result.success(list);
    }
}
