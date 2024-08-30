package com.xb.blog.message.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户私信联系人数据Vo
 */
@Data
public class ContactVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 私信表唯一id
     */
    private String uid;
    /**
     * 联系人唯一id
     */
    private String contactUid;
    /**
     * 联系人昵称
     */
    private String contactNickName;
    /**
     * 最新一条消息内容
     */
    private String chatContent;
    /**
     * 最新一条消息时间
     */
    private LocalDateTime lastTime;
    /**
     * 游标分页标记
     */
    private Long cur;
}
