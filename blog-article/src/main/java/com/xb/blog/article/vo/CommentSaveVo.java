package com.xb.blog.article.vo;

import lombok.Data;

@Data
public class CommentSaveVo {

    private String articleId;

    private String parentId;

    private String replyToId;

    private String content;
}
