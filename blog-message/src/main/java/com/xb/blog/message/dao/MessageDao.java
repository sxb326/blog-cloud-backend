package com.xb.blog.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.message.entity.MessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {
    /**
     * 根据消息id判断消息是否已存在
     *
     * @param uid
     * @return
     */
    Boolean exist(@Param("uid") String uid);

    /**
     * 根据用户id获取未读消息数量
     *
     * @param uid
     * @return
     */
    List<Long> getMessageCount(@Param("uid") String uid);
}
