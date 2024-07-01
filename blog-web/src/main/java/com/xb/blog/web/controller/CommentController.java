package com.xb.blog.web.controller;

import com.xb.blog.common.constants.Result;
import com.xb.blog.web.service.CommentService;
import com.xb.blog.web.vo.CommentSaveVo;
import com.xb.blog.web.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 保存评论
     *
     * @param vo
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody CommentSaveVo vo) {
        commentService.save(vo);
        return Result.success("操作成功");
    }

    /**
     * 根据传入的博客id以及一级评论id 获取到评论以及子评论数据
     *
     * @param blogId
     * @param commentId
     * @return
     */
    @GetMapping("/{blogId}/{commentId}")
    public Result getById(@PathVariable("blogId") String blogId, @PathVariable("commentId") String commentId) {
        CommentVo vo = commentService.getTreeDataById(blogId, commentId, null);
        return Result.success(vo);
    }
}
