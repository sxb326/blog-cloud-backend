package com.xb.blog.article.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.article.entity.ArticleTagEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleTagDao extends BaseMapper<ArticleTagEntity> {
}
