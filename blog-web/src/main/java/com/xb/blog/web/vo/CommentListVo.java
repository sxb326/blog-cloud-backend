package com.xb.blog.web.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentListVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 评论id
     */
    private String uid;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 回复的用户id
     */
    private String replyToUserId;
    /**
     * 回复的用户昵称
     */
    private String replyToUserNickName;
    /**
     * 评论点赞数
     */
    private Long likeCount;
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
    private String userPicUid;
    /**
     * 下级评论集合
     */
    private List<CommentListVo> subComments;
}
