package com.xb.blog.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xb.blog.common.constants.Result;
import com.xb.blog.web.entity.BlogEntity;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.vo.BlogPreviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/preview")
public class PreviewController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/{id}")
    public Result getBlogById(@PathVariable("id") String id, HttpServletResponse r) {
        BlogEntity blog = blogService.getById(id);
        if (blog != null) {
            BlogPreviewVo vo = new BlogPreviewVo();
            BeanUtil.copyProperties(blog, vo);
            return Result.success(vo);
        }
        return Result.redirect("文章不存在");
    }
}
