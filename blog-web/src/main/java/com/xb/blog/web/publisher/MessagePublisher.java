package com.xb.blog.web.publisher;

import com.xb.blog.common.rabbitmq.constants.RabbitMQConstants;
import com.xb.blog.common.rabbitmq.pojo.MessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class MessagePublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 将系统消息对象推送到rabbitmq
     *
     * @param type          消息类型 1点赞 2评论 3收藏 4关注 5私信 6通知
     * @param content
     * @param sendUserId
     * @param receiveUserId
     * @param blogUid
     * @param commentUid
     */
    public void sendMessage(int type, String content, String sendUserId, String receiveUserId, String blogUid, String commentUid) {
        if (sendUserId != null && !sendUserId.equals(receiveUserId)) {
            MessageDto dto = new MessageDto();

            dto.setUid(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            dto.setSendUserUid(sendUserId);
            dto.setReceiveUserUid(receiveUserId);
            dto.setType(type);
            dto.setBlogUid(blogUid);
            dto.setCommentUid(commentUid);
            dto.setContent(content);
            dto.setSendTime(LocalDateTime.now());

            rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGE_NAME, RabbitMQConstants.ROUTING_KEY_NAME, dto);
        }
    }
}
