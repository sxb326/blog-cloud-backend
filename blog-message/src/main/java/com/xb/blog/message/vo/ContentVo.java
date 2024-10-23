package com.xb.blog.message.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 私信消息数据Vo
 */
@Data
public class ContentVo {
    /**
     * 消息唯一id
     */
    private Long id;
    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 发送内容
     */
    private String content;
    /**
     * 是否为己方发送
     */
    private Boolean isSend;
}
