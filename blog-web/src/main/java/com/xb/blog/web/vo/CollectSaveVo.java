package com.xb.blog.web.vo;

import lombok.Data;

/**
 * 收藏文章时的数据Vo
 */
@Data
public class CollectSaveVo {
    private String blogId;
    private String favoriteId;
}
