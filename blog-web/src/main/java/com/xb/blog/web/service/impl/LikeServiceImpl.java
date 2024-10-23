package com.xb.blog.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.pojo.BlogDocument;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.web.dao.LikeDao;
import com.xb.blog.web.entity.CommentEntity;
import com.xb.blog.web.entity.LikeEntity;
import com.xb.blog.web.feign.SearchFeignService;
import com.xb.blog.web.publisher.MessagePublisher;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.service.CommentService;
import com.xb.blog.web.service.LikeService;
import com.xb.blog.web.vo.LikeSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeDao, LikeEntity> implements LikeService {

    @Autowired
    private BlogService blogService;

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
            blogService.updateLikeCount(vo.getObjId(), status ? 1L : -1L);

            //更新es
            BlogDocument doc = blogService.getBlogDocumentByBlogId(vo.getObjId());
            searchFeignService.publish(doc);

            //发送消息
            if (status) {
                messagePublisher.sendMessage(1, null, userId, doc.getAuthorId(), vo.getObjId(), null);
            }

            //返回最新点赞数
            return blogService.getLikeCount(vo.getObjId());
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
