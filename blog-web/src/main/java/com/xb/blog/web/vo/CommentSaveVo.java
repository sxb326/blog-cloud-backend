package com.xb.blog.web.vo;

import lombok.Data;

@Data
public class CommentSaveVo {

    private String blogUid;

    private String parentUid;

    private String replyToUid;

    private String content;
}
