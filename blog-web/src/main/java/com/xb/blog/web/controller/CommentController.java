package com.xb.blog.web.controller;

import com.xb.blog.common.constants.Result;
import com.xb.blog.web.service.CommentService;
import com.xb.blog.web.vo.CommentSaveVo;
import com.xb.blog.web.vo.LikeSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
