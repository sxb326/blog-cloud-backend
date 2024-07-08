package com.xb.blog.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户收藏表
 */
@Data
@TableName("t_collect")
public class CollectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String uid;
    /**
     * 博客uid
     */
    private String blogUid;
    /**
     * 用户uid
     */
    private String userUid;
    /**
     * 收藏夹uid
     */
    private String favoriteUid;
    /**
     * 逻辑删除 1：不删除，0：删除
     */
    @TableLogic(value = "1", delval = "0")
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
