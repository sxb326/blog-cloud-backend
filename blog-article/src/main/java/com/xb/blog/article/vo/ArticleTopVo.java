package com.xb.blog.article.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleTopVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文章id
     */
    private String id;
    /**
     * 标题
     */
    private String title;
}
