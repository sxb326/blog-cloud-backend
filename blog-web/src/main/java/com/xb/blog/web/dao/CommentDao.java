package com.xb.blog.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.web.dto.CommentDto;
import com.xb.blog.web.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
    /**
     * 根据博客id查询评论数据
     *
     * @param blogId
     * @param parentId 如果不为null 查询这个父id及其子评论数据
     * @param userId
     * @param page
     * @return
     */
    List<CommentDto> listPage(@Param("blogId") String blogId, @Param("parentId") String parentId,
                              @Param("userId") String userId, @Param("page") Long page);

    /**
     * 修改评论的点赞数
     *
     * @param commentId
     * @param count
     */
    void updateLikeCount(@Param("commentId") String commentId, @Param("count") Long count);

    /**
     * 根据评论id获取最新点赞数
     *
     * @param commentId
     * @return
     */
    Long getLikeCountByCommentId(@Param("commentId") String commentId);

    /**
     * 修改评论的评论数
     *
     * @param commentId
     * @param count
     */
    void updateCommentCount(@Param("commentId") String commentId, @Param("count") Long count);
}
