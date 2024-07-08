package com.xb.blog.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.web.entity.FavoriteEntity;
import com.xb.blog.web.vo.FavoriteVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FavoriteDao extends BaseMapper<FavoriteEntity> {
    /**
     * 查询用户的收藏夹列表
     *
     * @param userId
     * @return
     */
    List<FavoriteVo> listFavorite(@Param("userId") String userId);
}
