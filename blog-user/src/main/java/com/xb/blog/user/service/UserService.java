package com.xb.blog.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.user.entity.UserEntity;
import com.xb.blog.user.vo.UserInfoVo;

/**
 * 用户表
 *
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
public interface UserService extends IService<UserEntity> {
    UserEntity findByUsername(String username);

    UserInfoVo getUserInfo(String userId);

    void updatePassword(String username, String newPassword);
}

