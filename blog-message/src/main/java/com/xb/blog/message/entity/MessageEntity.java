package com.xb.blog.message.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("t_message")
public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
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
     * 文章id 点赞/评论/收藏 场景需要
     */
    private String articleId;
    /**
     * 评论id 评论 场景需要
     */
    private String commentId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 是否已接收
     */
    private Boolean isReceive;
    /**
     * 逻辑删除 1：不删除，0：删除
     */
    @TableLogic(value = "1", delval = "0")
    @TableField(fill = FieldFill.INSERT)
    private int status;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}
