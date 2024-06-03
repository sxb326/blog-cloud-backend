package com.xb.blog.common.dao;

import com.xb.blog.common.entity.BlogTagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 博客标签关联表
 * 
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
@Mapper
public interface BlogTagDao extends BaseMapper<BlogTagEntity> {
	
}
