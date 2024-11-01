package com.xb.blog.article.vo;

import lombok.Data;

/**
 * 收藏文章时的数据Vo
 */
@Data
public class CollectSaveVo {
    private String articleId;
    private String favoriteId;
}
