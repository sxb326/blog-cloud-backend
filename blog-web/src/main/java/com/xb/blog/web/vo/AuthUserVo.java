package com.xb.blog.web.vo;

import lombok.Data;

@Data
public class AuthUserVo {
    /**
     * ID
     */
    private String uid;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像图片id
     */
    private String picUid;
}
