package com.xb.blog.web.dao;

import com.xb.blog.web.entity.BlogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 博客表
 * 
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
@Mapper
public interface BlogDao extends BaseMapper<BlogEntity> {
	
}
