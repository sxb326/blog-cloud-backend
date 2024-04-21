package com.xb.blog.common.dao;

import com.xb.blog.common.entity.TagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签表
 * 
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
@Mapper
public interface TagDao extends BaseMapper<TagEntity> {
	
}
