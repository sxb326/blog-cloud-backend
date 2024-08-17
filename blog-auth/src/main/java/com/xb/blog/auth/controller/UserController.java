package com.xb.blog.auth.controller;

import com.xb.blog.auth.service.UserService;
import com.xb.blog.auth.vo.UserInfoVo;
import com.xb.blog.common.core.constants.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getUserInfo(@PathVariable("id") String id) {
        UserInfoVo vo = userService.getUserInfo(id);
        if (vo != null) {
            return Result.success(vo);
        }
        return Result.success(null);
    }
}
