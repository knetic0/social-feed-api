package com.mehmetsolak.social.domain.follow.events;

import com.mehmetsolak.social.domain.shared.DomainEvent;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.time.Instant;
import java.util.UUID;

public record UserFollowedEvent(
        UUID eventId,
        UserId followerId,
        UserId followeeId,
        Instant occurredAt
) implements DomainEvent { }
