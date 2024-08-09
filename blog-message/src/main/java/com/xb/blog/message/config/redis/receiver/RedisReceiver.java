package com.xb.blog.message.config.redis.receiver;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xb.blog.message.common.constants.RedisConstants;
import com.xb.blog.message.config.websocket.server.WebSocketServer;
import com.xb.blog.message.entity.MessageEntity;
import com.xb.blog.message.service.MessageService;
import com.xb.blog.message.vo.MessageCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisReceiver implements MessageListener {

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private MessageService messageService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String channel = new String(message.getChannel());
            String id = new String(message.getBody(), RedisConstants.UTF8);
            if (RedisConstants.REDIS_CHANNEL.endsWith(channel)) {
                //获取用户未接收的消息条数
                MessageCountVo vo = messageService.getMessageCount(id);
                webSocketServer.sendMessageCount(id, vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
