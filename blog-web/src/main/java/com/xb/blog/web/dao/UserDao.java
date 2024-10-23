package com.xb.blog.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.web.entity.UserEntity;
import com.xb.blog.web.vo.UserInfoVo;
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
    UserInfoVo getUserInfo(@Param("userUid") String userUid, @Param("loginUserUid") String loginUserUid);

}
