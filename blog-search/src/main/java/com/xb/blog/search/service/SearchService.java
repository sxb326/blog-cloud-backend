package com.xb.blog.search.service;


import com.xb.blog.common.core.pojo.ArticleDocument;
import com.xb.blog.common.core.vo.SearchVo;

import java.util.List;

/**
 * 检索
 */
public interface SearchService {
    /**
     * 根据传入的关键字以及分页参数，检索数据 封装为SearchVo并返回
     *
     * @param keyword
     * @param page
     * @return
     */
    SearchVo search(String keyword, Long page);

    /**
     * 根据传入的分页、分类id、排序参数，查询文章列表
     *
     * @param page
     * @param categoryId
     * @param orderType
     * @return
     */
    List<ArticleDocument> list(Long page, String categoryId, String orderType);

    /**
     * 根据传入的分页、用户id、排序参数，查询文章列表
     *
     * @param page
     * @param userId
     * @param orderType
     * @return
     */
    List<ArticleDocument> listByUser(Long page, String userId, String orderType);
}
