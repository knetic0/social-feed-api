package com.mehmetsolak.social.domain.post.events;

import com.mehmetsolak.social.domain.shared.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record PostCreatedEvent(
        UUID eventId, UUID postId, UUID authorId, Instant occurredAt) implements DomainEvent {
}
