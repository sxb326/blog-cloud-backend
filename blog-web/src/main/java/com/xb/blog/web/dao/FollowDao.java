package com.xb.blog.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.web.entity.FollowEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowDao extends BaseMapper<FollowEntity> {
}
