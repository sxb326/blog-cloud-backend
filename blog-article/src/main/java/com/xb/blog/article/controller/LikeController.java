package com.xb.blog.article.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.article.service.LikeService;
import com.xb.blog.article.vo.LikeSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * 保存点赞行为。返回最新点赞数
     *
     * @param vo
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody LikeSaveVo vo) {
        Long count = likeService.save(vo);
        return Result.success("操作成功",count);
    }
}
