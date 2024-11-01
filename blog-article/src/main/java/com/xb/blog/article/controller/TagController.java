package com.xb.blog.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.article.entity.TagEntity;
import com.xb.blog.article.service.TagService;
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
