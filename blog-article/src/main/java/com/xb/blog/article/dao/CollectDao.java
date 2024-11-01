package com.xb.blog.article.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.article.entity.CollectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectDao extends BaseMapper<CollectEntity> {
    /**
     * 判断收藏表中是否存在对应收藏记录
     *
     * @param articleId
     * @param favoriteId
     * @return
     */
    Boolean exist(@Param("articleId") String articleId, @Param("userId") String userId, @Param("favoriteId") String favoriteId);

    /**
     * 删除对应收藏记录
     *
     * @param articleId
     * @param userId
     * @param favoriteId
     */
    int delete(@Param("articleId") String articleId, @Param("userId") String userId, @Param("favoriteId") String favoriteId);
}
