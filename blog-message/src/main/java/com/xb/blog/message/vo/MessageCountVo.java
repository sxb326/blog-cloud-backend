package com.xb.blog.message.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageCountVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总消息数
     */
    private Long totalCount;
    /**
     * 点赞消息数
     */
    private Long likeCount;
    /**
     * 评论消息数
     */
    private Long commentCount;
    /**
     * 收藏消息数
     */
    private Long collectCount;
    /**
     * 关注消息数
     */
    private Long followCount;
    /**
     * 私信消息数
     */
    private Long chatCount;
    /**
     * 通知消息数
     */
    private Long noticeCount;
}
