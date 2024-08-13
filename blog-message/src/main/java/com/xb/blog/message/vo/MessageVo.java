package com.xb.blog.message.vo;

import lombok.Data;

/**
 * 消息列表数据Vo
 */
@Data
public class MessageVo {
    /**
     * 发送者id
     */
    private String sendUserUid;
    /**
     * 发送者昵称
     */
    private String sendUserNickName;
    /**
     * 发送者头像id
     */
    private String sendUserPicUid;
    /**
     * 博客id
     */
    private String blogUid;
    /**
     * 博客标题
     */
    private String blogTitle;
    /**
     * 评论内容
     */
    private String commentContent;
    /**
     * 消息内容
     */
    private String content;
}
