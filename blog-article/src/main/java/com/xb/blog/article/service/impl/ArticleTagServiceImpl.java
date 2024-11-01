package com.xb.blog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.article.dao.ArticleTagDao;
import com.xb.blog.article.entity.ArticleTagEntity;
import com.xb.blog.article.service.ArticleTagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTagEntity> implements ArticleTagService {
    /**
     * 根据传入的文章id 和 标签id集合，保存博客和标签的关联数据
     *
     * @param articleId
     * @param tagIds
     */
    @Override
    public void save(String articleId, List<String> tagIds) {
        remove(new QueryWrapper<ArticleTagEntity>().eq("article_id", articleId));
        if (tagIds != null) {
            List<ArticleTagEntity> entities = tagIds.stream()
                    .map(tagId -> {
                        ArticleTagEntity articleTagEntity = new ArticleTagEntity();
                        articleTagEntity.setArticleId(articleId);
                        articleTagEntity.setTagId(tagId);
                        articleTagEntity.setStatus(1);
                        return articleTagEntity;
                    }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }
}
