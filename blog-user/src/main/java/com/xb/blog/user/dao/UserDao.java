package com.xb.blog.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.common.core.pojo.UserInfo;
import com.xb.blog.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表
 *
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
    UserInfo getUserInfo(@Param("userId") String userId, @Param("loginUserId") String loginUserId);

    void updatePassword(@Param("username") String username, @Param("newPassword") String newPassword);
}
