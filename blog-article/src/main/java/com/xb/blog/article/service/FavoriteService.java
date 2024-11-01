package com.xb.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.article.entity.FavoriteEntity;
import com.xb.blog.article.vo.FavoriteVo;

import java.util.List;

public interface FavoriteService extends IService<FavoriteEntity> {
    /**
     * 查询用户的收藏夹列表
     *
     * @return
     */
    List<FavoriteVo> listFavorite(String articleId);

    /**
     * 创建默认收藏夹
     */
    void createDefaultFavorite();
}
