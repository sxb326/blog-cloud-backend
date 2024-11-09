package com.xb.blog.article.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.blog.article.entity.CategoryEntity;
import com.xb.blog.article.service.ArticleService;
import com.xb.blog.article.service.CategoryService;
import com.xb.blog.article.vo.ArticleListVo;
import com.xb.blog.article.vo.ArticleTopVo;
import com.xb.blog.common.core.constants.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result list(@RequestParam("page") Long page, @RequestParam("categoryId") String categoryId, @RequestParam("orderType") String orderType) {
        List<ArticleListVo> vos = articleService.list(page, categoryId, orderType);
        return Result.success(vos);
    }

    @GetMapping("/category")
    public Result category() {
        List<CategoryEntity> list = new ArrayList<>();
        CategoryEntity entity = new CategoryEntity();
        entity.setId("");
        entity.setName("全部");
        entity.setIcon("HomeFilled");
        list.add(entity);
        list.addAll(categoryService.list(new QueryWrapper<CategoryEntity>().orderByAsc("sort")));
        return Result.success(list);
    }
}
