package com.mehmetsolak.social.infrastructure.bean;

import com.mehmetsolak.social.application.shared.ports.DomainEventPublisher;
import com.mehmetsolak.social.infrastructure.events.SpringDomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {

    @Bean
    DomainEventPublisher domainEventPublisher(
            ApplicationEventPublisher applicationEventPublisher) {
        return new SpringDomainEventPublisher(applicationEventPublisher);
    }
}
