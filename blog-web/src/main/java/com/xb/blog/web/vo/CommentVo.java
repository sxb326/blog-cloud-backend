package com.xb.blog.web.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    /**
     * 评论数
     */
    private Long count;
    /**
     * 评论树数据
     */
    private List<CommentListVo> data;
}
