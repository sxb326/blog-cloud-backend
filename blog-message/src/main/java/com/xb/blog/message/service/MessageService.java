package com.xb.blog.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xb.blog.common.rabbitmq.pojo.MessageDto;
import com.xb.blog.message.entity.MessageEntity;
import com.xb.blog.message.vo.MessageCountVo;

public interface MessageService extends IService<MessageEntity> {
    /**
     * 保存消息到消息表，并推送给用户
     *
     * @param dto
     */
    void saveAndSend(MessageDto dto);

    /**
     * 根据用户id获取未读消息数量
     *
     * @param id
     * @return
     */
    MessageCountVo getMessageCount(String id);
}
