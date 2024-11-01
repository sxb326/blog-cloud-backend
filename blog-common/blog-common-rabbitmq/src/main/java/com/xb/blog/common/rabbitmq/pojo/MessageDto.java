package com.xb.blog.common.rabbitmq.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统消息
 */
@Data
public class MessageDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 消息唯一id
     */
    private String id;
    /**
     * 消息创建时间
     */
    private LocalDateTime sendTime;
    /**
     * 发送消息用户id
     */
    private String sendUserId;
    /**
     * 接收消息用户id
     */
    private String receiveUserId;
    /**
     * 消息类型 1点赞 2评论 3收藏 4关注 5私信 6通知
     */
    private int type;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 评论id
     */
    private String commentId;
    /**
     * 消息内容
     */
    private String content;
}
