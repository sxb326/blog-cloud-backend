package com.xb.blog.web.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户收藏夹表
 */
@Data
@TableName("t_favorite")
public class FavoriteEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String uid;
    /**
     * 收藏夹名称
     */
    private String name;
    /**
     * 用户id
     */
    private String userUid;
    /**
     * 是否为默认收藏夹
     */
    private int isDefault;
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
