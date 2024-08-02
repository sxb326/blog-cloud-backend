package com.xb.blog.common.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 博客发布时，上传到es的数据对象
 */
@Data
public class BlogDocument implements Serializable {
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
     * 作者ID
     */
    private String authorId;
    /**
     * 作者名称
     */
    private String authorName;
}
