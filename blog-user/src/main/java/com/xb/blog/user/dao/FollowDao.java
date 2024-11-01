package com.xb.blog.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.user.entity.FollowEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowDao extends BaseMapper<FollowEntity> {
}
