package com.xb.blog.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 博客表
 *
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
@Data
@TableName("t_blog")
public class BlogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String uid;
    /**
     * 博客标题
     */
    private String title;
    /**
     * 博客简介
     */
    private String summary;
    /**
     * 博客内容
     */
    private String content;
    /**
     * 封面图片id
     */
    private String picUid;
    /**
     * 标签集id
     */
    private String tagsUid;
    /**
     * 点击数
     */
    private Integer clickCount;
    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 收藏数
     */
    private Integer collectCount;
    /**
     * 作者id
     */
    private String author;
    /**
     * 是否发布 1：是，0：否
     */
    private Integer isPublish;
    /**
     * 排序字段
     */
    private Integer sort;
    /**
     * 逻辑删除 1：不删除，0：删除
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
