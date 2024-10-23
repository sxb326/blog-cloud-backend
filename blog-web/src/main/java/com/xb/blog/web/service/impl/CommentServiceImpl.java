package com.xb.blog.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.pojo.BlogDocument;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.web.dao.CommentDao;
import com.xb.blog.web.dto.CommentDto;
import com.xb.blog.web.entity.CommentEntity;
import com.xb.blog.web.feign.SearchFeignService;
import com.xb.blog.web.publisher.MessagePublisher;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.service.CommentService;
import com.xb.blog.web.vo.CommentListVo;
import com.xb.blog.web.vo.CommentSaveVo;
import com.xb.blog.web.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Autowired
    private BlogService blogService;

    @Autowired
    private SearchFeignService searchFeignService;

    @Autowired
    private MessagePublisher messagePublisher;

    /**
     * 根据博客id获取评论数据
     *
     * @param blogId
     * @param parentId 如果不为null 查询这个父id及其子评论数据
     * @param page
     * @return
     */
    @Override
    public CommentVo getTreeDataById(String blogId, String parentId, Long page) {
        if (page != null) page = (page - 1) * 10;
        List<CommentDto> list = baseMapper.listPage(blogId, parentId, UserUtil.getUserId(), page);
        CommentVo vo = new CommentVo();
        vo.setCount(blogService.getCommentCount(blogId));
        vo.setData(getSubComments(list, "0"));
        return vo;
    }

    /**
     * 修改点赞数
     *
     * @param commentId
     * @param count
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateLikeCount(String commentId, Long count) {
        baseMapper.updateLikeCount(commentId, count);
    }

    /**
     * 递归组装评论树
     *
     * @param list
     * @param parentId
     * @return
     */
    private List<CommentListVo> getSubComments(List<CommentDto> list, String parentId) {
        return list.stream()
                .filter(c -> parentId.equals(c.getParentId()))
                .map(c -> {
                    CommentListVo vo = new CommentListVo();
                    BeanUtil.copyProperties(c, vo);
                    vo.setSubComments(getSubComments(list, c.getId()));
                    return vo;
                }).collect(Collectors.toList());
    }

    /**
     * 保存评论 返回评论的评论数量和博客的评论数量
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void save(CommentSaveVo vo) {
        //保存评论表
        CommentEntity entity = new CommentEntity();
        entity.setType(1);
        entity.setObjId(vo.getBlogId());
        entity.setUserId(UserUtil.getUserId());
        entity.setParentId(vo.getParentId());
        entity.setReplyToId(StrUtil.isNotBlank(vo.getReplyToId()) ? vo.getReplyToId() : null);
        entity.setContent(vo.getContent());
        entity.setStatus(1);
        save(entity);

        //更新目标评论的评论数
        baseMapper.updateCommentCount(vo.getParentId(), 1L);
        if (StrUtil.isNotBlank(vo.getReplyToId())) {
            baseMapper.updateCommentCount(vo.getReplyToId(), 1L);
        }

        //更新博客评论数
        blogService.updateCommentCount(vo.getBlogId(), 1L);

        //更新es中的数据
        BlogDocument doc = blogService.getBlogDocumentByBlogId(vo.getBlogId());
        searchFeignService.publish(doc);

        //推送消息
        String receiveUserId = doc.getAuthorId();
        //如果当前为回复评论 则应发消息到发评论的人 而非文章作者
        if (!"0".equals(vo.getParentId())) {
            if (StrUtil.isNotBlank(vo.getReplyToId())) {
                receiveUserId = getById(vo.getReplyToId()).getUserId();
            } else {
                receiveUserId = getById(vo.getParentId()).getUserId();
            }
        }
        messagePublisher.sendMessage(2, null, UserUtil.getUserId(), receiveUserId, vo.getBlogId(), entity.getId());
    }

    /**
     * 根据id获取评论的点赞数
     *
     * @param objId
     * @return
     */
    @Override
    public Long getLikeCount(String objId) {
        return baseMapper.getLikeCountByCommentId(objId);
    }
}
