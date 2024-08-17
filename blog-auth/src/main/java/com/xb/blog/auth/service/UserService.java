package com.xb.blog.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.auth.entitiy.UserEntity;
import com.xb.blog.auth.vo.UserInfoVo;

/**
 * 用户表
 *
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
public interface UserService extends IService<UserEntity> {
    /**
     * 根据用户ID 获取用户信息
     *
     * @param id
     * @return
     */
    UserInfoVo getUserInfo(String userUid);
}

