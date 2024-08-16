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
     * 评论id
     */
    private String commentUid;
    /**
     * 评论内容
     */
    private String commentContent;
    /**
     * 评论的层级 0为评论文章 1为评论评论
     */
    private Integer commentLevel;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 发起时间
     */
    private String sendTimeBefore;
}
