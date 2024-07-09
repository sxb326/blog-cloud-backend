package com.xb.blog.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.web.entity.CollectEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectDao extends BaseMapper<CollectEntity> {
    /**
     * 判断收藏表中是否存在对应收藏记录
     *
     * @param blogId
     * @param favoriteId
     * @return
     */
    Boolean exist(@Param("blogId") String blogId, @Param("userId") String userId, @Param("favoriteId") String favoriteId);

    /**
     * 删除对应收藏记录
     *
     * @param blogId
     * @param userId
     * @param favoriteId
     */
    int delete(@Param("blogId") String blogId, @Param("userId") String userId, @Param("favoriteId") String favoriteId);
}
