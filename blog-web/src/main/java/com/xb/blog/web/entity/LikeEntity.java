package com.xb.blog.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("t_like")
public class LikeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String uid;
    /**
     * 点赞类型 1文章点赞 2评论点赞
     */
    private int type;
    /**
     * 点赞目标id 点赞文章时为文章id 点赞评论时为评论id
     */
    private String objUid;
    /**
     * 点赞用户id
     */
    private String userUid;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
