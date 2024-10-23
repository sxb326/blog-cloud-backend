package com.xb.blog.web.vo;

import lombok.Data;

@Data
public class CommentSaveVo {

    private String blogId;

    private String parentId;

    private String replyToId;

    private String content;
}
