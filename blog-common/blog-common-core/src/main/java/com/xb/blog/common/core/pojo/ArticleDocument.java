package com.xb.blog.common.core.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 博客发布时，上传到es的数据对象
 */
@Data
public class ArticleDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    private String id;
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
    private String picId;
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
    /**
     * 分类id
     */
    private String categoryId;
    /**
     * 标签id
     */
    private List<String> tagIdList;
    /**
     * 标签集合
     */
    private List<String> tagNameList;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;
}
