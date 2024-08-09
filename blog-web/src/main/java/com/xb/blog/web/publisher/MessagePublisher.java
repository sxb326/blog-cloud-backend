package com.xb.blog.web.publisher;

import com.xb.blog.common.rabbitmq.pojo.MessageDto;
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

    /**
     * 将系统消息对象推送到rabbitmq
     *
     * @param type
     * @param content
     * @param sendUserId
     * @param receiveUserId
     */
    public void sendMessage(String type, String content, String sendUserId, String receiveUserId) {
        MessageDto dto = new MessageDto();

        dto.setUid(UUID.randomUUID().toString().replace("-", "").toLowerCase());
        dto.setSendUserUid(sendUserId);
        dto.setReceiveUserUid(receiveUserId);
        dto.setType(type);
        dto.setContent(content);
        dto.setSendTime(LocalDateTime.now());

        rabbitTemplate.convertAndSend(exchangeName, routingKeyName, dto);
    }
}
