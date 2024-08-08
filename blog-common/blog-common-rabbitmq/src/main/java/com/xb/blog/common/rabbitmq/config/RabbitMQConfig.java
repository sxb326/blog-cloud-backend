package com.xb.blog.common.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 */
@Configuration
public class RabbitMQConfig {

    @Value("${message.exchange-name}")
    private String exchangeName;

    @Value("${message.routing-key-name}")
    private String routingKeyName;

    @Value("${message.queue-name}")
    private String queueName;

    @Bean
    public Queue messageQueue() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Exchange messageExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding messageBinding() {
        return BindingBuilder.bind(messageQueue()).to(messageExchange()).with(routingKeyName).noargs();
    }
}
