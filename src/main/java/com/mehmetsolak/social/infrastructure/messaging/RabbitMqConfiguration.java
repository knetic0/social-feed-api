package com.mehmetsolak.social.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.mehmetsolak.social.infrastructure.messaging.RabbitMqConstants.*;

@Configuration
public class RabbitMqConfiguration {

    @Bean
    public TopicExchange socialExchange() {
        return new TopicExchange(SOCIAL_EXCHANGE, true, false);
    }

    @Bean
    public Queue postCreatedQueue() {
        return QueueBuilder.durable(POST_CREATED_QUEUE).build();
    }

    @Bean
    public Binding postCreatedBinding(
            Queue postCreatedQueue,
            TopicExchange socialExchange
    ) {
        return BindingBuilder
                .bind(postCreatedQueue)
                .to(socialExchange)
                .with(POST_CREATED_ROUTING_KEY);
    }
}
