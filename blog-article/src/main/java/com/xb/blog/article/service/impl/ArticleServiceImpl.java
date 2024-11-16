package com.xb.blog.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.article.dao.ArticleDao;
import com.xb.blog.article.entity.ArticleEntity;
import com.xb.blog.article.feign.SearchFeignService;
import com.xb.blog.article.service.ArticleService;
import com.xb.blog.article.service.ArticleTagService;
import com.xb.blog.article.service.DraftService;
import com.xb.blog.article.vo.ArticleEditorVo;
import com.xb.blog.article.vo.ArticleListVo;
import com.xb.blog.article.vo.ArticlePreviewVo;
import com.xb.blog.article.vo.ArticleTopVo;
import com.xb.blog.common.core.pojo.ArticleDocument;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.common.core.vo.SearchVo;
import com.xb.blog.common.redis.constants.RedisKeyConstants;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleEntity> implements ArticleService {

    @Autowired
    private DraftService draftService;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SearchFeignService searchFeignService;

    /**
     * 发布博客
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(ArticleEditorVo vo) {
        //如果文章没有被保存过，初始化布隆过滤器，用于保存点击过该文章的ip
        if (getById(vo.getId()) == null) {
            redissonClient.getBloomFilter(RedisKeyConstants.ARTICLE_CLICK_IP_FILTER + vo.getId()).tryInit(10000L, 0.01);
        }

        //保存博客表
        ArticleEntity article = new ArticleEntity();
        BeanUtil.copyProperties(vo, article);
        article.setStatus(1);
        article.setAuthor(UserUtil.getUserId());
        saveOrUpdate(article);

        //保存标签绑定数据
        articleTagService.save(vo.getId(), vo.getTagIds());

        //删除草稿表
        draftService.removeById(vo.getId());

        //调用检索服务，将博客检索信息上传到es
        updateToEsSync(article.getId());
    }

    /**
     * 根据id获取博客预览数据
     *
     * @param id
     * @return
     */
    @Override
    public ArticlePreviewVo getArticlePreviewById(String id) {
        return baseMapper.getArticlePreviewById(id, UserUtil.getUserId());
    }

    /**
     * 修改点赞数
     *
     * @param articleId 文章id
     * @param count     点赞数 1/-1
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateLikeCount(String articleId, Long count) {
        baseMapper.updateLikeCount(articleId, count);
    }

    /**
     * 修改评论数
     *
     * @param articleId
     * @param count
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateCommentCount(String articleId, Long count) {
        baseMapper.updateCommentCount(articleId, count);
    }

    /**
     * 根据文章id获取博客的评论数
     *
     * @param articleId
     * @return
     */
    @Override
    public Long getCommentCount(String articleId) {
        return baseMapper.getCommentCountByArticleId(articleId);
    }

    /**
     * 根据文章id修改博客的点击量
     *
     * @param articleId
     * @param count
     */
    @Override
    public void updateClickCount(String articleId, Long count) {
        baseMapper.updateClickCount(articleId, count);
    }

    /**
     * 修改博客收藏数 并返回最新收藏数
     *
     * @param articleId
     * @param count
     * @return
     */
    @Override
    public void updateCollectCount(String articleId, Long count) {
        baseMapper.updateCollectCount(articleId, count);
    }

    /**
     * 根据文章id获取博客的点赞数
     *
     * @param articleId
     * @return
     */
    @Override
    public Long getLikeCount(String articleId) {
        return baseMapper.getLikeCountByArticleId(articleId);
    }

    /**
     * 根据文章id 查询博客的收藏数
     *
     * @param id
     * @return
     */
    @Override
    public Long getCollectCount(String id) {
        return baseMapper.getCollectCountByArticleId(id);
    }

    @Override
    public ArticleDocument getArticleDocumentByArticleId(String articleId) {
        return baseMapper.getArticleDocumentByArticleId(articleId);
    }

    @Override
    public ArticleDocument updateToEs(String articleId) {
        ArticleDocument doc = baseMapper.getArticleDocumentByArticleId(articleId);
        searchFeignService.publish(doc);
        return doc;
    }

    @Override
    public ArticleDocument updateToEsSync(String articleId) {
        ArticleDocument doc = baseMapper.getArticleDocumentByArticleId(articleId);
        searchFeignService.publishSync(doc);
        return doc;
    }
}