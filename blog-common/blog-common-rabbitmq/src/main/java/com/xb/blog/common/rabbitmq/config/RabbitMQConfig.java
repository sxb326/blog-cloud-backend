package com.xb.blog.common.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置类
 */
//@Configuration
//public class RabbitMQConfig {
//
//    @Bean
//    public Queue messageQueue() {
//        return QueueBuilder.durable(RabbitMQConstants.MESSAGE_QUEUE.getValue()).build();
//    }
//
//    @Bean
//    public Exchange messageExchange() {
//        return new TopicExchange(RabbitMQConstants.MESSAGE_EXCHANGE.getValue());
//    }
//
//    @Bean
//    public Binding messageBinding() {
//        return BindingBuilder.bind(messageQueue()).to(messageExchange()).with(RabbitMQConstants.MESSAGE_ROUTING_KEY.getValue()).noargs();
//    }
//}
