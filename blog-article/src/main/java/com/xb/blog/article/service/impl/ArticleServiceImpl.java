package com.xb.blog.article.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.core.pojo.ArticleDocument;
import com.xb.blog.common.core.utils.UserUtil;
import com.xb.blog.common.core.vo.SearchVo;
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
    private SearchFeignService searchFeignService;

    /**
     * 发布博客
     *
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(ArticleEditorVo vo) {
        //保存博客表
        ArticleEntity blog = new ArticleEntity();
        BeanUtil.copyProperties(vo, blog);
        blog.setStatus(1);
        blog.setAuthor(UserUtil.getUserId());
        saveOrUpdate(blog);

        //保存标签绑定数据
        articleTagService.save(vo.getId(), vo.getTagIds());

        //删除草稿表
        draftService.removeById(vo.getId());

        //调用检索服务，将博客检索信息上传到es
        ArticleDocument doc = getArticleDocumentByArticleId(blog.getId());
        searchFeignService.publish(doc);
    }

    /**
     * 查询博客列表
     *
     * @return
     */
    @Override
    public List<ArticleListVo> list(Long page, String categoryId, String orderType) {
        //处理特殊情况
        if (page == null) page = 1L;

        //处理文章分类
        String category = StrUtil.isNotBlank(categoryId) ? "_" + categoryId : "_ALL";

        //处理排序方式
        String orderTypeUpper = StrUtil.isNotBlank(orderType) ? orderType.toUpperCase() : "";


        //定义缓存Keu格式（每页数据单独缓存，且固定每页条数为10条）
        String lockKey = "HOME_ARTICLE_LIST_LOCK_CATEGORY" + category + "_ORDER_BY_" + orderTypeUpper + "_SIZE_10_PAGE_" + page;
        String dataKey = "HOME_ARTICLE_LIST_DATA_CATEGORY" + category + "_ORDER_BY_" + orderTypeUpper + "_SIZE_10_PAGE_" + page;

        //换算分页参数（使用OFFSET关键字进行分页，故此处起始页码应为0）
        page = (page - 1L) * 10L;

        //从缓存中获取数据
        String cache = redisTemplate.opsForValue().get(dataKey);
        if (StrUtil.isNotBlank(cache)) {
            //缓存中有数据 直接返回
            return JSONUtil.toList(cache, ArticleListVo.class);
        }

        //获取分布式锁
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid);

        if (lock) {
            //拿到分布式锁，查询数据库
            try {
                List<ArticleListVo> list = baseMapper.list(page, categoryId, orderType, null, UserUtil.getUserId());
                if (CollUtil.isEmpty(list)) {
                    //设置空值 避免缓存穿透
                    redisTemplate.opsForValue().set(dataKey, JSONUtil.toJsonStr(list), 10, TimeUnit.SECONDS);
                } else {
                    redisTemplate.opsForValue().set(dataKey, JSONUtil.toJsonStr(list), 1, TimeUnit.MINUTES);
                }
                return list;
            } finally {
                //使用lua脚本 保证释放分布式锁的原子性
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                redisTemplate.execute(new DefaultRedisScript<>(script, Long.class)
                        , Arrays.asList(lockKey), uuid);
            }
        }

        //未拿到分布式锁，进行一定次数的重试
        for (int i = 0; i < 3; i++) {
            try {
                //适当休眠 避免cpu空转
                Thread.sleep(200);

                //再次查询缓存
                cache = redisTemplate.opsForValue().get(dataKey);
                if (StrUtil.isNotBlank(cache)) {
                    //缓存中有数据 直接返回
                    return JSONUtil.toList(cache, ArticleListVo.class);
                }

            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }

        //重试获取数据失败 返回空集合
        return Collections.emptyList();
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
     * @param count  点赞数 1/-1
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
     * 因为修改点击量的场景是在预览文章时，所以这里采用异步调用的方式，不影响文章数据的返回
     *
     * @param articleId
     * @param count
     */
    @Async
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

    /**
     * 根据传入的搜索关键字以及分页参数 返回查询数据
     *
     * @param keyword
     * @param page
     * @return
     */
    @Override
    public SearchVo search(String keyword, Long page) {
        if (page == null) page = 1L;
        page = (page - 1L) * 10L;

        Integer total = baseMapper.selectCount(new QueryWrapper<ArticleEntity>().eq("status", 1).like("title", keyword));
        List<ArticleDocument> list = baseMapper.search(keyword, page);

        SearchVo vo = new SearchVo();
        vo.setKeyword(keyword);
        vo.setTotal(total.longValue());
        vo.setList(list);
        return vo;
    }

    /**
     * 根据文章id 封装BlogDocument 用于 发送给es保存数据 以及 检索页面返回
     *
     * @param articleId
     * @return
     */
    @Override
    public ArticleDocument getArticleDocumentByArticleId(String articleId) {
        return baseMapper.getArticleDocumentByArticleId(articleId);
    }

    /**
     * 获取文章推荐排行前10的文章
     *
     * @return
     */
    @Override
    public List<ArticleTopVo> getTop10List() {
        return baseMapper.getTop10List();
    }

    /**
     * 列出用户所有文章
     *
     * @param page
     * @param userId
     * @param orderType
     * @return
     */
    @Override
    public List<ArticleListVo> listByUser(Long page, String userId, String orderType) {
        //处理特殊情况
        if (page == null) page = 1L;
        //换算分页参数（使用OFFSET关键字进行分页，故此处起始页码应为0）
        page = (page - 1L) * 10L;
        List<ArticleListVo> list = baseMapper.list(page, null, orderType, userId, UserUtil.getUserId());
        return list;
    }
}