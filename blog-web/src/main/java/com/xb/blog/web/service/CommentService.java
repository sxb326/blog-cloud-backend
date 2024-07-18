package com.xb.blog.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.web.entity.CommentEntity;
import com.xb.blog.web.vo.CommentSaveVo;
import com.xb.blog.web.vo.CommentVo;

public interface CommentService extends IService<CommentEntity> {

    /**
     * 根据博客id获取评论数据
     *
     * @param blogId   博客id
     * @param parentId 如果不为null 查询这个父id及其子评论数据
     * @param page     分页参数 用于评论列表懒加载。不需要可传null
     * @return
     */
    CommentVo getTreeDataById(String blogId, String parentId, Long page);

    /**
     * 修改点赞数
     *
     * @param commentId
     * @param count
     * @return
     */
    void updateLikeCount(String commentId, Long count);

    /**
     * 保存评论
     *
     * @param vo
     * @return
     */
    void save(CommentSaveVo vo);
}
