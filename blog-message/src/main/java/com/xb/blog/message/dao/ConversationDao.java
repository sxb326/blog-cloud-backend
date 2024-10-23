package com.xb.blog.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.message.entity.ConversationEntity;
import com.xb.blog.message.vo.ConversationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConversationDao extends BaseMapper<ConversationEntity> {
    List<ConversationVo> list(@Param("userId") String userId, @Param("keyword") String keyword);

    Boolean exists(@Param("sendUserId") String sendUserId, @Param("receiveUserId") String receiveUserId);

    void updateNotReceiveCount(@Param("id") String conversationId, @Param("count") int count);

    void clearNotReceiveCount(@Param("id") String conversationId);
}
