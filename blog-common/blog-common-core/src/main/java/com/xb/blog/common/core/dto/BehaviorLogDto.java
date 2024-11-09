package com.xb.blog.common.core.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户行为记录
 */
@Data
public class BehaviorLogDto {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 操作类型：阅读、点赞、评论、收藏、关注
     */
    private String behaviorType;
    /**
     * 目标用户id：文章作者id或被关注用户id
     */
    private String targetUserId;
    /**
     * 文章分类id
     */
    private String categoryId;
    /**
     * 文章标签id
     */
    private List<String> tagIds;
    /**
     * 行为时间
     */
    private LocalDateTime behaviorTime = LocalDateTime.now();
}
