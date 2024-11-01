package com.xb.blog.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.article.dao.FavoriteDao;
import com.xb.blog.article.entity.FavoriteEntity;
import com.xb.blog.article.service.FavoriteService;
import com.xb.blog.article.vo.FavoriteVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteDao, FavoriteEntity> implements FavoriteService {
    /**
     * 查询用户的收藏夹列表
     *
     * @return
     */
    @Override
    public List<FavoriteVo> listFavorite(String articleId) {
        String userId = UserUtil.getUserId();
        return baseMapper.listFavorite(userId, articleId);
    }

    /**
     * 创建默认收藏夹
     */
    @Override
    public void createDefaultFavorite() {
        FavoriteEntity entity = new FavoriteEntity();
        entity.setName("我的收藏");
        entity.setIsDefault(1);
        entity.setStatus(1);
        entity.setUserId(UserUtil.getUserId());
        save(entity);
    }
}
