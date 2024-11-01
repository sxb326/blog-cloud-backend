package com.xb.blog.article.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.article.service.CommentService;
import com.xb.blog.article.vo.CommentSaveVo;
import com.xb.blog.article.vo.CommentVo;
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
     * 根据传入的文章id以及一级评论id 获取到评论以及子评论数据
     *
     * @param articleId
     * @param commentId
     * @return
     */
    @GetMapping("/{articleId}/{commentId}")
    public Result getById(@PathVariable("articleId") String articleId, @PathVariable("commentId") String commentId) {
        CommentVo vo = commentService.getTreeDataById(articleId, commentId, null);
        return Result.success(vo);
    }
}
