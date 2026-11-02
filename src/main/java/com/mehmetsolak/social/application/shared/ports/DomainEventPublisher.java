package com.mehmetsolak.social.application.shared.ports;

import com.mehmetsolak.social.domain.shared.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
