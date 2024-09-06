package com.xb.blog.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.message.entity.ConversationEntity;
import com.xb.blog.message.vo.ConversationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConversationDao extends BaseMapper<ConversationEntity> {
    List<ConversationVo> list(@Param("userUid") String userUid, @Param("keyword") String keyword);

    Boolean exists(@Param("sendUserUid") String sendUserUid, @Param("receiveUserUid") String receiveUserUid);

    void updateNotReceiveCount(@Param("id") String conversationUid, @Param("count") int count);

    void clearNotReceiveCount(@Param("id") String conversationUid);
}
