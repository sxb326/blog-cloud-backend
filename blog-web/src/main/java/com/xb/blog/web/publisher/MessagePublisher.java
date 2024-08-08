package com.xb.blog.web.publisher;

import com.xb.blog.common.rabbitmq.pojo.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class MessagePublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${message.exchange-name}")
    private String exchangeName;

    @Value("${message.routing-key-name}")
    private String routingKeyName;

    public void sendMessage(String type, String content, String sendUserId, String receiveUserId) {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        message.setSendUserId(sendUserId);
        message.setReceiveUserId(receiveUserId);
        message.setType(type);
        message.setContent(content);
        message.setCreateTime(LocalDateTime.now());

        rabbitTemplate.convertAndSend(exchangeName, routingKeyName, message);
    }
}
