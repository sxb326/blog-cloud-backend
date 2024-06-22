package com.xb.blog.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 博客预览时的数据Vo
 */
@Data
public class BlogPreviewVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    private String uid;
    /**
     * 博客标题
     */
    private String title;
    /**
     * 博客内容
     */
    private String content;
}
