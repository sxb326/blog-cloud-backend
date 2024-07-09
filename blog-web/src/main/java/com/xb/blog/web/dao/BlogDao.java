package com.xb.blog.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.web.entity.BlogEntity;
import com.xb.blog.web.vo.BlogListVo;
import com.xb.blog.web.vo.BlogPreviewVo;
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
public interface BlogDao extends BaseMapper<BlogEntity> {
    /**
     * 查询博客列表数据
     *
     * @return
     */
    List<BlogListVo> listBlog(@Param("page") Long page);

    /**
     * 博客预览时 查询出预览数据
     *
     * @param uid
     * @param userUid
     * @return
     */
    BlogPreviewVo getBlogPreviewById(@Param("uid") String uid, @Param("userUid") String userUid);

    /**
     * 根据博客id 更新点赞数
     *
     * @param blogId
     * @param count
     */
    void updateLikeCount(@Param("blogId") String blogId, @Param("count") Long count);

    /**
     * 根据博客id 查询出点赞数
     *
     * @param blogId
     * @return
     */
    Long getLikeCountByBlogId(String blogId);

    /**
     * 根据博客id 更新评论数
     *
     * @param blogId
     * @param count
     */
    void updateCommentCount(@Param("blogId") String blogId, @Param("count") Long count);

    /**
     * 根据博客id 查询出评论数
     *
     * @param blogId
     * @return
     */
    Long getCommentCountByBlogId(String blogId);

    /**
     * 根据博客id 修改点击数
     *
     * @param blogId
     * @param count
     */
    void updateClickCount(@Param("blogId") String blogId, @Param("count") Long count);

    /**
     * 修改博客收藏数
     *
     * @param blogId
     * @param count
     */
    void updateCollectCount(@Param("blogId") String blogId, @Param("count") Long count);

    /**
     * 查询博客收藏数
     *
     * @param blogId
     * @return
     */
    Long getCollectCountByBlogId(@Param("blogId") String blogId);
}
