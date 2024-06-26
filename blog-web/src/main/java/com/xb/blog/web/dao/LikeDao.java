package com.xb.blog.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.web.entity.LikeEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeDao extends BaseMapper<LikeEntity> {
}
