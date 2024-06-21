package com.xb.blog.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.blog.common.constants.Result;
import com.xb.blog.web.entity.CategoryEntity;
import com.xb.blog.web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result list() {
        List<CategoryEntity> list = categoryService.list(new QueryWrapper<CategoryEntity>().orderByAsc("sort"));
        return Result.success(list);
    }
}