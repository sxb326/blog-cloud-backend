package com.xb.blog.web.vo;

import lombok.Data;

/**
 * 编辑博客时的数据Vo
 */
@Data
public class BlogEditorVo {
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
