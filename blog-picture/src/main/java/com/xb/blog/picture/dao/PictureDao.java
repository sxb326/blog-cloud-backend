package com.xb.blog.picture.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.picture.entity.PictureEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PictureDao extends BaseMapper<PictureEntity> {
}
