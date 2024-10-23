package com.xb.blog.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.message.entity.ChatEntity;
import com.xb.blog.message.vo.ContentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatDao extends BaseMapper<ChatEntity> {
    List<ContentVo> list(@Param("userId") String userId, @Param("contactId") String contactId, @Param("cursor") Long cursor);

    List<ContentVo> newest(@Param("userId") String userId, @Param("contactId") String contactId, @Param("cursor") Long cursor);
}
