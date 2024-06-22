package com.xb.blog.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 首页列表数据Vo
 */
@Data
public class BlogListVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 博客ID
     */
    private String uid;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String summary;
    /**
     * 封面图片id
     */
    private String picUid;
    /**
     * 点击数
     */
    private Long clickCount;
    /**
     * 点赞数
     */
    private Long likeCount;
    /**
     * 收藏数
     */
    private Long collectCount;
    /**
     * 作者ID
     */
    private String authorId;
    /**
     * 作者名称
     */
    private String authorName;
}
