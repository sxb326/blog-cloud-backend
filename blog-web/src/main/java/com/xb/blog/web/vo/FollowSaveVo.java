package com.xb.blog.web.vo;

import lombok.Data;

@Data
public class FollowSaveVo {
    private String targetUserUid;
    private Boolean isFollow;
}
