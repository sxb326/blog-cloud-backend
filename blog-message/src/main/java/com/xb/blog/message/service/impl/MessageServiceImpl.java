package com.xb.blog.message.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.rabbitmq.pojo.MessageDto;
import com.xb.blog.message.dao.MessageDao;
import com.xb.blog.message.entity.MessageEntity;
import com.xb.blog.message.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {
    /**
     * 保存消息到消息表，并推送给用户
     *
     * @param dto
     */
    @Override
    public void saveAndSend(MessageDto dto) {
        //保存消息
        MessageEntity entity = new MessageEntity();
        BeanUtil.copyProperties(dto, entity);
        entity.setIsReceive(false);
        entity.setStatus(1);
        saveOrUpdate(entity);

        //todo 推送给用户
    }
}
