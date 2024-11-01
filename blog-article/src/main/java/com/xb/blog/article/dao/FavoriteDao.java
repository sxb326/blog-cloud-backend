package com.xb.blog.article.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.article.entity.FavoriteEntity;
import com.xb.blog.article.vo.FavoriteVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FavoriteDao extends BaseMapper<FavoriteEntity> {
    /**
     * 查询用户的收藏夹列表
     *
     * @param userId 用户id
     * @param articleId 文章id 当收藏文章时打开 需要判断哪个收藏夹收藏了该文章
     * @return
     */
    List<FavoriteVo> listFavorite(@Param("userId") String userId, @Param("articleId") String articleId);
}
