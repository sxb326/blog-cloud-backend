package com.xb.blog.auth.controller;

import com.xb.blog.common.constants.Result;
import com.xb.blog.common.utils.AuthUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @GetMapping("isLogin")
    public Result isLogin(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        return Result.success(AuthUtil.isLogin(token));
    }
}
