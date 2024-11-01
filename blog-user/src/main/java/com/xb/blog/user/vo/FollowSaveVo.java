package com.xb.blog.user.vo;

import lombok.Data;

@Data
public class FollowSaveVo {
    private String targetUserId;
    private Boolean isFollow;
}
