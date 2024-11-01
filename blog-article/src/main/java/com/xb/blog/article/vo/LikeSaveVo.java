package com.xb.blog.article.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 保存点赞数据Vo
 */
@Data
public class LikeSaveVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 点赞类型：1文章点赞 2评论点赞
     */
    private int type;
    /**
     * 点赞目标id 点赞文章时为文章id 点赞评论时为评论id
     */
    private String objId;
    /**
     * 点赞状态 true为点赞 false为取消点赞
     */
    private Boolean status;
}
