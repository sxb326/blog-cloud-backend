package com.xb.blog.web.controller;

import cn.hutool.core.util.StrUtil;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.dto.AuthUserDto;
import com.xb.blog.common.core.utils.AuthUtil;
import com.xb.blog.web.entity.UserEntity;
import com.xb.blog.web.service.UserService;
import com.xb.blog.web.vo.AuthUserVo;
import com.xb.blog.web.vo.UserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findByUsername/{username}")
    public Result<AuthUserDto> findByUsername(@PathVariable("username") String username) {
        UserEntity user = userService.findByUsername(username);
        if (user != null) {
            AuthUserDto dto = new AuthUserDto();
            dto.setUid(user.getUid());
            dto.setUsername(user.getUsername());
            dto.setPassword(user.getPassword());
            return Result.success("查询成功", dto);
        }
        return Result.error("用户不存在！");
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
                UserEntity user = userService.findByUsername(username);
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
    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam("id") String id) {
        UserInfoVo vo = userService.getUserInfo(id);
        if (vo != null) {
            return Result.success(vo);
        }
        return Result.success(null);
    }
}
