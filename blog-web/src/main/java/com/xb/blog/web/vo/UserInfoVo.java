package com.xb.blog.web.vo;

import lombok.Data;

@Data
public class UserInfoVo {
    /**
     * 唯一ID
     */
    private String uid;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像图片id
     */
    private String picUid;
    /**
     * 总博客数
     */
    private Long blogCount;
    /**
     * 总点击数
     */
    private Long clickCount;
    /**
     * 总点赞数
     */
    private Long likeCount;
    /**
     * 是否已关注当前用户
     */
    private Boolean isFollow;
    /**
     * 粉丝数
     */
    private Long followerCount;
    /**
     * 关注数
     */
    private Long followingCount;
}
