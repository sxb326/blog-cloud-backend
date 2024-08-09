package com.xb.blog.message.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xb.blog.common.rabbitmq.pojo.MessageDto;
import com.xb.blog.message.config.websocket.server.WebSocketServer;
import com.xb.blog.message.dao.MessageDao;
import com.xb.blog.message.entity.MessageEntity;
import com.xb.blog.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 保存消息到消息表，并推送给用户
     *
     * @param dto
     */
    @Override
    public void saveAndSend(MessageDto dto) {
        //保存消息
        String uid = dto.getUid();
        Boolean exist = baseMapper.exist(uid);
        if (!exist) {
            MessageEntity entity = new MessageEntity();
            BeanUtil.copyProperties(dto, entity);
            entity.setIsReceive(false);
            save(entity);

            //推送未读消息条数给用户
            webSocketServer.send(dto.getReceiveUserUid());
        }
    }
}
