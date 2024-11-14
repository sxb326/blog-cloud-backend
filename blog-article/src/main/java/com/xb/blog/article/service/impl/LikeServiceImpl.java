package com.xb.blog.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.article.common.constants.BehaviorType;
import com.xb.blog.article.dao.LikeDao;
import com.xb.blog.article.entity.CommentEntity;
import com.xb.blog.article.entity.LikeEntity;
import com.xb.blog.article.feign.SearchFeignService;
import com.xb.blog.article.service.ArticleService;
import com.xb.blog.article.service.CommentService;
import com.xb.blog.article.service.LikeService;
import com.xb.blog.article.vo.LikeSaveVo;
import com.xb.blog.common.core.dto.BehaviorLogDto;
import com.xb.blog.common.core.pojo.ArticleDocument;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.common.rabbitmq.publisher.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeDao, LikeEntity> implements LikeService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private SearchFeignService searchFeignService;

    @Autowired
    private MessagePublisher messagePublisher;

    /**
     * 保存点赞行为
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Long save(LikeSaveVo vo) {
        String userId = UserUtil.getUserId();

        //保存点赞表
        Boolean status = vo.getStatus();
        if (status) {
            LikeEntity entity = new LikeEntity();
            BeanUtil.copyProperties(vo, entity);
            entity.setUserId(userId);
            save(entity);
        } else {
            QueryWrapper<LikeEntity> wrapper = new QueryWrapper<LikeEntity>()
                    .eq("type", vo.getType())
                    .eq("obj_id", vo.getObjId())
                    .eq("user_id", userId);
            remove(wrapper);
        }

        //如果当前type=1 更新博客点赞数；如果type=2 更新评论点赞数
        int type = vo.getType();
        if (type == 1) {
            //更新数据库
            articleService.updateLikeCount(vo.getObjId(), status ? 1L : -1L);

            //更新es
            ArticleDocument doc = articleService.updateToEs(vo.getObjId());

            //发送消息
            if (status) {
                messagePublisher.sendMessage(1, null, userId, doc.getAuthorId(), vo.getObjId(), null);
            }

            //保存行为数据到es
            if (status && !userId.equals(doc.getAuthorId())) {
                BehaviorLogDto dto = new BehaviorLogDto();
                dto.setUserId(userId);
                dto.setBehaviorType(BehaviorType.LIKE.name());
                dto.setCategoryId(doc.getCategoryId());
                dto.setTagIds(doc.getTagIdList());
                dto.setTargetUserId(doc.getAuthorId());
                searchFeignService.saveBehaviorLog(dto);
            }

            //返回最新点赞数
            return articleService.getLikeCount(vo.getObjId());
        } else if (type == 2) {
            //更新数据库
            commentService.updateLikeCount(vo.getObjId(), status ? 1L : -1L);

            //发送消息
            CommentEntity commentEntity = commentService.getById(vo.getObjId());
            if (status) {
                messagePublisher.sendMessage(1, null, userId, commentEntity.getUserId(), commentEntity.getObjId(), vo.getObjId());
            }

            //返回最新点赞数
            return commentEntity.getLikeCount();
        }
        return null;
    }
}
