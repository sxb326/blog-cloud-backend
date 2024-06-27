package com.xb.blog.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.web.entity.CommentEntity;
import com.xb.blog.web.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
    /**
     * 根据博客id查询评论数据
     *
     * @param blogId
     * @return
     */
    List<CommentDto> listPage(@Param("blogId") String blogId, @Param("page") Long page);
}
