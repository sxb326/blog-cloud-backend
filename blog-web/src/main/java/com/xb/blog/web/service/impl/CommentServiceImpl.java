package com.xb.blog.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.common.utils.UserUtil;
import com.xb.blog.web.dao.CommentDao;
import com.xb.blog.web.dto.CommentDto;
import com.xb.blog.web.entity.CommentEntity;
import com.xb.blog.web.service.BlogService;
import com.xb.blog.web.service.CommentService;
import com.xb.blog.web.vo.CommentListVo;
import com.xb.blog.web.vo.CommentSaveVo;
import com.xb.blog.web.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Autowired
    private BlogService blogService;

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
    public Long updateLikeCount(String commentId, Long count) {
        baseMapper.updateLikeCount(commentId, count);
        return baseMapper.getLikeCountByCommentId(commentId);
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
                .filter(c -> parentId.equals(c.getParentUid()))
                .map(c -> {
                    CommentListVo vo = new CommentListVo();
                    BeanUtil.copyProperties(c, vo);
                    vo.setSubComments(getSubComments(list, c.getUid()));
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
        entity.setObjUid(vo.getBlogUid());
        entity.setUserUid(UserUtil.getUserId());
        entity.setParentUid(vo.getParentUid());
        entity.setReplyToUid(StrUtil.isNotBlank(vo.getReplyToUid()) ? vo.getReplyToUid() : null);
        entity.setContent(vo.getContent());
        entity.setStatus(1);
        save(entity);

        Map<String, Long> map = new HashMap<>();

        //更新目标评论的评论数
        baseMapper.updateCommentCount(vo.getParentUid(), 1L);
        if (StrUtil.isNotBlank(vo.getReplyToUid())) {
            baseMapper.updateCommentCount(vo.getReplyToUid(), 1L);
        }

        //更新博客评论数
        blogService.updateCommentCount(vo.getBlogUid(), 1L);
    }
}
