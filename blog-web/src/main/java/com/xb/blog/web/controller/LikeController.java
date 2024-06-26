package com.xb.blog.web.controller;

import com.xb.blog.common.constants.Result;
import com.xb.blog.web.service.LikeService;
import com.xb.blog.web.vo.LikeSaveVo;
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
     * 保存点赞行为
     *
     * @param vo
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody LikeSaveVo vo) {
        likeService.save(vo);
        return Result.success("操作成功");
    }
}
