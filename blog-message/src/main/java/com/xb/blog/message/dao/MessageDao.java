package com.xb.blog.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xb.blog.message.entity.MessageEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {
}
