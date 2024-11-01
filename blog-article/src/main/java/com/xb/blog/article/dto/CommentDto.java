package com.xb.blog.article.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 评论id
     */
    private String id;
    /**
     * 评论父级id
     */
    private String parentId;
    /**
     * 回复的用户id
     */
    private String replyToUserId;
    /**
     * 回复的用户昵称
     */
    private String replyToUserNickName;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论点赞数
     */
    private Long likeCount;
    /**
     * 是否已点赞
     */
    private Boolean liked;
    /**
     * 评论数
     */
    private Long commentCount;
    /**
     * 评论时间
     */
    private LocalDateTime createTime;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String userNickName;
    /**
     * 用户头像id
     */
    private String userPicId;
    /**
     * 当前评论用户是否为作者
     */
    private Boolean isAuthor;
    /**
     * 当前回复对象是否为作者
     */
    private Boolean isReplyToAuthor;
}
