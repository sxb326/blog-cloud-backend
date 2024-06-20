package com.xb.blog.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xb.blog.common.constants.Result;
import com.xb.blog.common.utils.AuthUtil;
import com.xb.blog.web.entity.BlogEntity;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.vo.BlogEditorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/getBlogById/{id}")
    public Result getBlogById(HttpServletRequest request, @PathVariable("id") String id) {
        BlogEntity blog = blogService.getById(id);
        if (blog == null) {
            return Result.error(null);
        }
        String uid = AuthUtil.getLoginUid(request.getHeader("Token"));
        if (!uid.equals(blog.getAuthor())) {
            return Result.error("没有权限");
        }
        BlogEditorVo vo = new BlogEditorVo();
        BeanUtil.copyProperties(blog, vo);
        return Result.success(vo);
    }
}
