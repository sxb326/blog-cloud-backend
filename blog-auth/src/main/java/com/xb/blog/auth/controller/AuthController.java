package com.xb.blog.auth.controller;

import cn.hutool.core.util.StrUtil;
import com.xb.blog.auth.config.security.service.AuthUserDetailsService;
import com.xb.blog.auth.entitiy.UserEntity;
import com.xb.blog.auth.service.UserService;
import com.xb.blog.auth.vo.AuthUserVo;
import com.xb.blog.common.constants.Result;
import com.xb.blog.common.utils.AuthUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    @Autowired
    private UserService userService;

    /**
     * 校验当前用户登录状态 已登录 data返回用户的头像uid 未登录 data返回null
     *
     * @param request
     * @return
     */
    @GetMapping("/checkLoginStatus")
    public Result checkLoginStatus(HttpServletRequest request) {
        String token = request.getHeader("Token");
        if (StrUtil.isNotBlank(token)) {
            Boolean isAuth = AuthUtil.isAuth(token);
            if (isAuth) {
                return Result.success(true);
            }
        }
        return Result.success(false);
    }

    /**
     * 获取登录用户的信息
     *
     * @param request
     * @return
     */
    @GetMapping("/getAuthUser")
    public Result getAuthUser(HttpServletRequest request) {
        String token = request.getHeader("Token");
        if (StrUtil.isNotBlank(token)) {
            Boolean isAuth = AuthUtil.isAuth(token);
            if (isAuth) {
                String username = AuthUtil.getLoginUsername(token);
                UserDetails user = authUserDetailsService.loadUserByUsername(username);
                AuthUserVo authUser = new AuthUserVo();
                BeanUtils.copyProperties(user, authUser);
                return Result.success(authUser);
            }
        }
        return Result.success(null);
    }

    /**
     * 根据id获取用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getUserInfo/{id}")
    public Result getUserInfo(@PathVariable("id") String id) {
        UserEntity user = userService.getById(id);
        if (user != null) {
            AuthUserVo vo = new AuthUserVo();
            BeanUtils.copyProperties(user, vo);
            return Result.success(vo);
        }
        return Result.success(null);
    }
}
