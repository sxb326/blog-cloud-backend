package com.xb.blog.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.web.common.utils.UserUtil;
import com.xb.blog.web.dao.CommentDao;
import com.xb.blog.web.entity.CommentEntity;
import com.xb.blog.web.service.CommentService;
import com.xb.blog.web.dto.CommentDto;
import com.xb.blog.web.vo.CommentListVo;
import com.xb.blog.web.vo.CommentVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {
    /**
     * 获取评论树数据
     *
     * @param id
     * @return
     */
    @Override
    public CommentVo getTreeDataById(String id, Long page) {
        page = (page - 1) * 10;
        List<CommentDto> list = baseMapper.listPage(id, UserUtil.getUserId(), page);
        CommentVo vo = new CommentVo();
        vo.setCount(Long.valueOf(baseMapper.selectCount(new QueryWrapper<CommentEntity>().eq("blog_uid", id))));
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
}
