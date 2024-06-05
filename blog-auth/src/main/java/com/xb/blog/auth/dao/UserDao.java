package com.xb.blog.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.auth.entitiy.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 *
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}