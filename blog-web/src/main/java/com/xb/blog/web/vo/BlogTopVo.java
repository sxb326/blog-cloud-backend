package com.xb.blog.web.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlogTopVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 博客ID
     */
    private String id;
    /**
     * 标题
     */
    private String title;
}
