package com.mehmetsolak.social.infrastructure.events;

import com.mehmetsolak.social.application.shared.ports.DomainEventPublisher;
import com.mehmetsolak.social.domain.shared.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(DomainEvent event) {
        eventPublisher.publishEvent(event);
    }
}
