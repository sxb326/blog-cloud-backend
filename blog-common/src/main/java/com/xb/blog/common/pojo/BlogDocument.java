package com.xb.blog.common.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 博客发布时，上传到es的数据对象
 */
@Data
public class BlogDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uid;
    private String title;
}
