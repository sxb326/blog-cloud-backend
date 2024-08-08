package com.xb.blog.common.rabbitmq.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 消息唯一id
     */
    private String id;
    /**
     * 发送消息用户id
     */
    private String sendUserId;
    /**
     * 接收消息用户id
     */
    private String receiveUserId;
    /**
     * 消息类型
     */
    private String type;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息创建时间
     */
    private LocalDateTime createTime;
}
