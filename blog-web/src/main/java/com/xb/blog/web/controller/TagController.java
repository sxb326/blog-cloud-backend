package com.xb.blog.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.blog.common.constants.Result;
import com.xb.blog.web.entity.CategoryEntity;
import com.xb.blog.web.entity.TagEntity;
import com.xb.blog.web.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public Result list() {
        List<TagEntity> list = tagService.list(new QueryWrapper<TagEntity>().orderByAsc("sort"));
        return Result.success(list);
    }
}
