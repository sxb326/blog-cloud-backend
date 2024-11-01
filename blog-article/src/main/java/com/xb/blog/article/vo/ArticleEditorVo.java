package com.xb.blog.article.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 编辑博客时的数据Vo
 */
@Data
public class ArticleEditorVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    private String id;
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
    private String picId;
    /**
     * 标签集id
     */
    private List<String> tagIds;
    /**
     * 分类id
     */
    private String categoryId;
}
