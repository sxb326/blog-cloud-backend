package com.xb.blog.web.vo;

import com.xb.blog.common.utils.AuthUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 编辑博客时的数据Vo
 */
@Data
public class BlogEditorVo implements Serializable {
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
    private List<String> tagUids;
    /**
     * 分类id
     */
    private String categoryUid;
}
