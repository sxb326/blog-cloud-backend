package com.xb.blog.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.web.entity.BlogEntity;
import com.xb.blog.web.vo.BlogEditorVo;
import com.xb.blog.web.vo.BlogListVo;
import com.xb.blog.web.vo.BlogPreviewVo;

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
    List<BlogListVo> listBlog();

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
    Long updateLikeCount(String blogId, Long count);

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
}

