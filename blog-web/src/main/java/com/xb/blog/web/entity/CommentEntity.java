package com.xb.blog.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("t_comment")
public class CommentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 评论类型 1文章 2待定
     */
    private int type;
    /**
     * 评论目标id 评论文章时为文章id 其它待定
     */
    private String objId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 父级评论id 根评论的父级id为0
     */
    private String parentId;
    /**
     * 回复评论id
     */
    private String replyToId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论点赞数
     */
    private Long likeCount;
    /**
     * 评论数
     */
    private Long commentCount;
    /**
     * 逻辑删除 1：不删除，0：删除
     */
    @TableLogic(value = "1", delval = "0")
    private Integer status;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
