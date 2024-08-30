package com.xb.blog.message.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("t_chat")
public class ChatEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String uid;
    /**
     * 发送消息用户id
     */
    private String sendUserUid;
    /**
     * 接收消息用户id
     */
    private String receiveUserUid;
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
