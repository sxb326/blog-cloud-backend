package com.xb.blog.web.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.common.utils.UserUtil;
import com.xb.blog.web.dao.CollectDao;
import com.xb.blog.web.entity.CollectEntity;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.service.CollectService;
import com.xb.blog.web.vo.CollectSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectDao, CollectEntity> implements CollectService {

    @Autowired
    private BlogService blogService;

    /**
     * 收藏文章
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void save(CollectSaveVo vo) {
        String blogId = vo.getBlogId();
        String favoriteId = vo.getFavoriteId();
        String userId = UserUtil.getUserId();
        if (StrUtil.isBlank(favoriteId)) {
            //取消收藏场景
            int deleteCount = baseMapper.delete(blogId, userId, favoriteId);
            if (deleteCount > 0) {
                blogService.updateCollectCount(blogId, -1L);
            }
            return;
        }
        //新增收藏场景
        Boolean exist = baseMapper.exist(blogId, userId, favoriteId);
        if (!exist) {
            int deleteCount = baseMapper.delete(blogId, userId, null);
            if (deleteCount == 0) {
                blogService.updateCollectCount(blogId, 1L);
            }
            //保存收藏数据
            CollectEntity entity = new CollectEntity();
            entity.setBlogUid(blogId);
            entity.setFavoriteUid(favoriteId);
            entity.setUserUid(userId);
            save(entity);
        }
    }
}
