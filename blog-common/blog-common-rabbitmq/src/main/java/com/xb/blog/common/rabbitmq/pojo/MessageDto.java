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
    private String uid;
    /**
     * 消息创建时间
     */
    private LocalDateTime sendTime;
    /**
     * 发送消息用户id
     */
    private String sendUserUid;
    /**
     * 接收消息用户id
     */
    private String receiveUserUid;
    /**
     * 消息类型 1点赞 2评论 3收藏 4关注 5私信 6通知
     */
    private int type;
    /**
     * 博客id
     */
    private String blogUid;
    /**
     * 评论id
     */
    private String commentUid;
    /**
     * 消息内容
     */
    private String content;
}
