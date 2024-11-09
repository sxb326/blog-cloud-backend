package com.xb.blog.article.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.common.core.pojo.ArticleDocument;
import com.xb.blog.article.entity.ArticleEntity;
import com.xb.blog.article.vo.ArticleListVo;
import com.xb.blog.article.vo.ArticlePreviewVo;
import com.xb.blog.article.vo.ArticleTopVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 博客表
 *
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
@Mapper
public interface ArticleDao extends BaseMapper<ArticleEntity> {
    /**
     * 查询博客列表数据
     *
     * @return
     */
    List<ArticleListVo> list(@Param("page") Long page, @Param("categoryId") String categoryId, @Param("orderType") String orderType, @Param("userId") String userId, @Param("loginUserId") String loginUserId);

    /**
     * 博客预览时 查询出预览数据
     *
     * @param id
     * @param userId
     * @return
     */
    ArticlePreviewVo getArticlePreviewById(@Param("id") String id, @Param("userId") String userId);

    /**
     * 根据文章id 更新点赞数
     *
     * @param articleId
     * @param count
     */
    void updateLikeCount(@Param("articleId") String articleId, @Param("count") Long count);

    /**
     * 根据文章id 查询出点赞数
     *
     * @param articleId
     * @return
     */
    Long getLikeCountByArticleId(String articleId);

    /**
     * 根据文章id 更新评论数
     *
     * @param articleId
     * @param count
     */
    void updateCommentCount(@Param("articleId") String articleId, @Param("count") Long count);

    /**
     * 根据文章id 查询出评论数
     *
     * @param articleId
     * @return
     */
    Long getCommentCountByArticleId(String articleId);

    /**
     * 根据文章id 修改点击数
     *
     * @param articleId
     * @param count
     */
    void updateClickCount(@Param("articleId") String articleId, @Param("count") Long count);

    /**
     * 修改博客收藏数
     *
     * @param articleId
     * @param count
     */
    void updateCollectCount(@Param("articleId") String articleId, @Param("count") Long count);

    /**
     * 查询博客收藏数
     *
     * @param articleId
     * @return
     */
    Long getCollectCountByArticleId(@Param("articleId") String articleId);

    /**
     * 根据文章id 封装ArticleDocument 用于 发送给es保存数据 以及 检索页面返回
     *
     * @param articleId
     * @return
     */
    ArticleDocument getArticleDocumentByArticleId(@Param("articleId") String articleId);
}
