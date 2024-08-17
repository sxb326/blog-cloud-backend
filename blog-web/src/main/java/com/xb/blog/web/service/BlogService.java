package com.xb.blog.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.common.core.pojo.BlogDocument;
import com.xb.blog.common.core.vo.SearchVo;
import com.xb.blog.web.entity.BlogEntity;
import com.xb.blog.web.vo.BlogEditorVo;
import com.xb.blog.web.vo.BlogListVo;
import com.xb.blog.web.vo.BlogPreviewVo;
import com.xb.blog.web.vo.BlogTopVo;

import java.util.List;

/**
 * 博客表
 *
 * @author shixianbiao
 * @email sxb0326@qq.com
 * @date 2024-04-07 22:57:53
 */
public interface BlogService extends IService<BlogEntity> {
    /**
     * 发布博客
     *
     * @param vo
     */
    void publish(BlogEditorVo vo);

    /**
     * 查询博客列表
     *
     * @return
     */
    List<BlogListVo> listBlog(Long page, String categoryUid, String orderType);

    /**
     * 根据id获取博客预览数据
     *
     * @param id
     * @return
     */
    BlogPreviewVo getBlogPreviewById(String id);

    /**
     * 修改点赞数
     *
     * @param blogId 博客id
     * @param count  点赞数 1/-1
     * @return
     */
    void updateLikeCount(String blogId, Long count);

    /**
     * 根据博客id获取博客的点赞数
     *
     * @param blogId
     * @return
     */
    Long getLikeCount(String blogId);

    /**
     * 修改评论数
     *
     * @param blogId
     * @return
     */
    void updateCommentCount(String blogId, Long count);

    /**
     * 根据博客id获取博客的评论数
     *
     * @param blogId
     * @return
     */
    Long getCommentCount(String blogId);

    /**
     * 根据博客id修改博客的点击量
     *
     * @param blogId
     * @param count
     */
    void updateClickCount(String blogId, Long count);

    /**
     * 修改博客收藏数 并返回最新收藏数
     *
     * @param blogId
     * @param count
     * @return
     */
    void updateCollectCount(String blogId, Long count);

    /**
     * 根据博客id 查询博客的收藏数
     *
     * @param id
     * @return
     */
    Long getCollectCount(String id);

    /**
     * 根据传入的搜索关键字以及分页参数 返回查询数据
     *
     * @param keyword
     * @param page
     * @return
     */
    SearchVo search(String keyword, Long page);

    /**
     * 根据博客id 获取到最新数据 更新es中的数据
     *
     * @param blogId
     * @return
     */
    BlogDocument getBlogDocumentByBlogId(String blogId);

    /**
     * 获取文章推荐排行前10的文章
     *
     * @return
     */
    List<BlogTopVo> getTop10List();

    /**
     * 列出用户所有文章
     *
     * @param page
     * @param userUid
     * @param orderType
     * @return
     */
    List<BlogListVo> listBlogByUser(Long page, String userUid, String orderType);
}

