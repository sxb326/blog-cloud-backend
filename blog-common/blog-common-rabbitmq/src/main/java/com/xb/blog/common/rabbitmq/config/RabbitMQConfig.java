package com.xb.blog.common.rabbitmq.config;

import com.xb.blog.common.rabbitmq.constants.RabbitMQConstants;
import com.xb.blog.common.rabbitmq.publisher.MessagePublisher;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue messageQueue() {
        return QueueBuilder.durable(RabbitMQConstants.QUEUE_NAME).build();
    }

    @Bean
    public Exchange messageExchange() {
        return new TopicExchange(RabbitMQConstants.EXCHANGE_NAME);
    }

    @Bean
    public Binding messageBinding() {
        return BindingBuilder.bind(messageQueue()).to(messageExchange()).with(RabbitMQConstants.ROUTING_KEY_NAME).noargs();
    }

    @Bean
    public MessagePublisher messagePublisher() {
        return new MessagePublisher();
    }
}
