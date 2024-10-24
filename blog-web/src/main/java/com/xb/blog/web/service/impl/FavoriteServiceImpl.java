package com.xb.blog.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.web.dao.FavoriteDao;
import com.xb.blog.web.entity.FavoriteEntity;
import com.xb.blog.web.service.FavoriteService;
import com.xb.blog.web.vo.FavoriteVo;
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
    public List<FavoriteVo> listFavorite(String blogId) {
        String userId = UserUtil.getUserId();
        return baseMapper.listFavorite(userId, blogId);
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
