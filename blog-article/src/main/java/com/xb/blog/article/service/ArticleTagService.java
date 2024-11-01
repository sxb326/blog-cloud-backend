package com.xb.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.article.entity.ArticleTagEntity;

import java.util.List;

public interface ArticleTagService extends IService<ArticleTagEntity> {
    /**
     * 根据传入的文章id 和 标签id集合，保存博客和标签的关联数据
     *
     * @param articleId
     * @param tagIds
     */
    void save(String articleId, List<String> tagIds);
}
