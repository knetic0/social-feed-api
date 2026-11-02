package com.mehmetsolak.social.infrastructure.messaging;

import com.mehmetsolak.social.domain.post.events.PostCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.RoutingTarget;

@Configuration
public class RabbitMqEventExternalizationConfiguration {

    @Bean
    public EventExternalizationConfiguration eventExternalizationConfiguration() {
        return EventExternalizationConfiguration
                .externalizing()
                .select(event -> event instanceof PostCreatedEvent)
                .route(
                        PostCreatedEvent.class,
                        _ -> RoutingTarget
                                .forTarget(RabbitMqConstants.SOCIAL_EXCHANGE)
                                .andKey(RabbitMqConstants.POST_CREATED_ROUTING_KEY)
                )
                .build();
    }
}
