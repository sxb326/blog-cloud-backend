package com.xb.blog.web.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.pojo.BlogDocument;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.web.dao.CollectDao;
import com.xb.blog.web.entity.CollectEntity;
import com.xb.blog.web.feign.SearchFeignService;
import com.xb.blog.web.publisher.MessagePublisher;
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

    @Autowired
    private SearchFeignService searchFeignService;

    @Autowired
    private MessagePublisher messagePublisher;

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

        Boolean isNewCollect = false;

        if (StrUtil.isBlank(favoriteId)) {
            //取消收藏场景
            int deleteCount = baseMapper.delete(blogId, userId, favoriteId);
            if (deleteCount > 0) {
                blogService.updateCollectCount(blogId, -1L);
            }
        } else {
            //新增收藏场景
            Boolean exist = baseMapper.exist(blogId, userId, favoriteId);
            if (!exist) {
                isNewCollect = true;
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
        //更新es中的数据
        BlogDocument doc = blogService.getBlogDocumentByBlogId(blogId);
        searchFeignService.publish(doc);

        //推送消息
        if (isNewCollect) {
            messagePublisher.sendMessage(3, "有人收藏了您的文章", UserUtil.getUserId(), doc.getAuthorId());
        }
    }
}
