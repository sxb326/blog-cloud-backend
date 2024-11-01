package com.xb.blog.user.vo;

import lombok.Data;

@Data
public class UserInfoVo {
    /**
     * 唯一ID
     */
    private String id;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像图片id
     */
    private String picId;
    /**
     * 总文章数
     */
    private Long articleCount;
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
