package com.xb.blog.user.controller;

import cn.hutool.core.util.StrUtil;
import com.xb.blog.common.core.annotation.InternalApi;
import com.xb.blog.common.core.constants.Result;
import com.xb.blog.common.core.dto.AuthUserDto;
import com.xb.blog.common.core.pojo.UserInfo;
import com.xb.blog.common.core.utils.AuthUtil;
import com.xb.blog.user.entity.UserEntity;
import com.xb.blog.user.service.UserService;
import com.xb.blog.user.vo.AuthUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @InternalApi
    @GetMapping("/findByUsername/{username}")
    public Result<AuthUserDto> findByUsername(@PathVariable("username") String username) {
        UserEntity user = userService.findByUsername(username);
        if (user != null) {
            AuthUserDto dto = new AuthUserDto();
            dto.setId(user.getId());
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
                if (user != null) {
                    AuthUserVo authUser = new AuthUserVo();
                    BeanUtils.copyProperties(user, authUser);
                    return Result.success(authUser);
                }
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
    public Result<UserInfo> getUserInfo(@RequestParam("id") String id) {
        UserInfo userInfo = userService.getUserInfo(id);
        if (userInfo == null || StrUtil.isBlank(userInfo.getId())) {
            throw new RuntimeException("该用户不存在！");
        }
        return Result.success(userInfo);
    }

    @InternalApi
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestParam("username") String username, @RequestParam("newPassword") String newPassword) {
        userService.updatePassword(username, newPassword);
        return Result.success();
    }
}
