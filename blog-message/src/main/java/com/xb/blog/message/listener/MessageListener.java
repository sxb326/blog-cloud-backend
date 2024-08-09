package com.xb.blog.message.listener;

import com.rabbitmq.client.Channel;
import com.xb.blog.common.rabbitmq.pojo.MessageDto;
import com.xb.blog.message.service.MessageService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "${message.queue-name}")
public class MessageListener {

    @Autowired
    private MessageService messageService;

    /**
     * 接收系统消息
     *
     * @param dto
     * @param channel
     * @param message
     * @throws IOException
     */
    @RabbitHandler
    public void onMessage(MessageDto dto, Channel channel, Message message) throws IOException {
        try {
            //保存到消息表 并推送给用户
            messageService.saveAndSend(dto);
            //手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //手动拒绝
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
