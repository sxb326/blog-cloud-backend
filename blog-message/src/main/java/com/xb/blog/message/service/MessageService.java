package com.xb.blog.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.common.rabbitmq.pojo.MessageDto;
import com.xb.blog.message.entity.MessageEntity;

public interface MessageService extends IService<MessageEntity> {
    /**
     * 保存消息到消息表，并推送给用户
     *
     * @param dto
     */
    void saveAndSend(MessageDto dto);
}
