package com.xb.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.article.entity.CommentEntity;
import com.xb.blog.article.vo.CommentSaveVo;
import com.xb.blog.article.vo.CommentVo;

public interface CommentService extends IService<CommentEntity> {

    /**
     * 根据文章id获取评论数据
     *
     * @param articleId   文章id
     * @param parentId 如果不为null 查询这个父id及其子评论数据
     * @param page     分页参数 用于评论列表懒加载。不需要可传null
     * @return
     */
    CommentVo getTreeDataById(String articleId, String parentId, Long page);

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

    /**
     * 根据id获取评论的点赞数
     *
     * @param objId
     * @return
     */
    Long getLikeCount(String objId);
}
