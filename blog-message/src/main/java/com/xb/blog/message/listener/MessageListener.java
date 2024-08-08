package com.xb.blog.message.listener;

import com.xb.blog.common.rabbitmq.pojo.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @RabbitListener(queues = "${message.queue-name}")
    public void onMessage(Message message) {
        System.out.println("消费消息：" + message);
    }
}
