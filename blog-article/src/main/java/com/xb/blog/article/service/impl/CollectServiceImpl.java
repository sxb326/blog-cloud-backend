package com.xb.blog.article.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.article.common.constants.BehaviorType;
import com.xb.blog.article.dao.CollectDao;
import com.xb.blog.article.entity.CollectEntity;
import com.xb.blog.article.feign.SearchFeignService;
import com.xb.blog.article.service.ArticleService;
import com.xb.blog.article.service.CollectService;
import com.xb.blog.article.vo.CollectSaveVo;
import com.xb.blog.common.core.dto.BehaviorLogDto;
import com.xb.blog.common.core.pojo.ArticleDocument;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.common.rabbitmq.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectDao, CollectEntity> implements CollectService {

    @Autowired
    private ArticleService articleService;

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
        String articleId = vo.getArticleId();
        String favoriteId = vo.getFavoriteId();
        String userId = UserUtil.getUserId();

        Boolean isNewCollect = false;

        if (StrUtil.isBlank(favoriteId)) {
            //取消收藏场景
            int deleteCount = baseMapper.delete(articleId, userId, favoriteId);
            if (deleteCount > 0) {
                articleService.updateCollectCount(articleId, -1L);
            }
        } else {
            //新增收藏场景
            Boolean exist = baseMapper.exist(articleId, userId, favoriteId);
            if (!exist) {
                isNewCollect = true;
                int deleteCount = baseMapper.delete(articleId, userId, null);
                if (deleteCount == 0) {
                    articleService.updateCollectCount(articleId, 1L);
                }
                //保存收藏数据
                CollectEntity entity = new CollectEntity();
                entity.setArticleId(articleId);
                entity.setFavoriteId(favoriteId);
                entity.setUserId(userId);
                save(entity);
            }
        }
        //更新es中的数据
        ArticleDocument doc = articleService.getArticleDocumentByArticleId(articleId);
        searchFeignService.publish(doc);

        //推送消息
        if (isNewCollect) {
            messagePublisher.sendMessage(3, null, UserUtil.getUserId(), doc.getAuthorId(), articleId, null);
        }

        //保存行为数据到es
        if (!userId.equals(doc.getAuthorId())) {
            BehaviorLogDto dto = new BehaviorLogDto();
            dto.setUserId(userId);
            dto.setBehaviorType(BehaviorType.COLLECT.name());
            dto.setCategoryId(doc.getCategoryId());
            dto.setTagIds(doc.getTagIdList());
            dto.setTargetUserId(doc.getAuthorId());
            searchFeignService.saveBehaviorLog(dto);
        }
    }
}
