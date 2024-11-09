package com.xb.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.article.entity.ArticleEntity;
import com.xb.blog.article.vo.ArticleEditorVo;
import com.xb.blog.article.vo.ArticlePreviewVo;
import com.xb.blog.common.core.pojo.ArticleDocument;

/**
 * 博客表
 *
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
public interface ArticleService extends IService<ArticleEntity> {
    /**
     * 发布博客
     *
     * @param vo
     */
    void publish(ArticleEditorVo vo);

    /**
     * 根据id获取博客预览数据
     *
     * @param id
     * @return
     */
    ArticlePreviewVo getArticlePreviewById(String id);

    /**
     * 修改点赞数
     *
     * @param articleId 文章id
     * @param count  点赞数 1/-1
     * @return
     */
    void updateLikeCount(String articleId, Long count);

    /**
     * 根据文章id获取博客的点赞数
     *
     * @param articleId
     * @return
     */
    Long getLikeCount(String articleId);

    /**
     * 修改评论数
     *
     * @param articleId
     * @return
     */
    void updateCommentCount(String articleId, Long count);

    /**
     * 根据文章id获取博客的评论数
     *
     * @param articleId
     * @return
     */
    Long getCommentCount(String articleId);

    /**
     * 根据文章id修改博客的点击量
     *
     * @param articleId
     * @param count
     */
    void updateClickCount(String articleId, Long count);

    /**
     * 修改博客收藏数 并返回最新收藏数
     *
     * @param articleId
     * @param count
     * @return
     */
    void updateCollectCount(String articleId, Long count);

    /**
     * 根据文章id 查询博客的收藏数
     *
     * @param id
     * @return
     */
    Long getCollectCount(String id);

    /**
     * 根据文章id 获取到最新数据 更新es中的数据
     *
     * @param articleId
     * @return
     */
    ArticleDocument getArticleDocumentByArticleId(String articleId);
}

