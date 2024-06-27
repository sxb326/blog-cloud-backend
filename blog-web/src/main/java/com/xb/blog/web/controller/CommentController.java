package com.xb.blog.web.controller;

import com.xb.blog.common.constants.Result;
import com.xb.blog.web.service.CommentService;
import com.xb.blog.web.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}/{page}")
    public Result getById(@PathVariable("id") String id, @PathVariable("page") Long page) {
        CommentVo vo = commentService.getTreeDataById(id,page);
        return Result.success(vo);
    }
}
