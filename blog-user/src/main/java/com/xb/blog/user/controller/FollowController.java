package com.xb.blog.user.controller;

import com.xb.blog.common.core.constants.Result;
import com.xb.blog.user.service.FollowService;
import com.xb.blog.user.vo.FollowSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/save")
    public Result save(@RequestBody FollowSaveVo vo) {
        followService.save(vo);
        return Result.success();
    }
}
