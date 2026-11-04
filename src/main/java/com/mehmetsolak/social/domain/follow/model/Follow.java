package com.mehmetsolak.social.domain.follow.model;

import com.mehmetsolak.social.domain.follow.events.UserFollowedEvent;
import com.mehmetsolak.social.domain.follow.exception.CannotFollowYourselfException;
import com.mehmetsolak.social.domain.shared.AggregateRoot;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.time.Instant;
import java.util.UUID;

public final class Follow extends AggregateRoot<UUID> {

    private final UUID id;
    private final UserId followerId;
    private final UserId followeeId;
    private final Instant createdAt;

    private Follow(
            UUID id,
            UserId followerId,
            UserId followeeId,
            Instant createdAt
    ) {
        if (followerId.equals(followeeId)) {
            throw new CannotFollowYourselfException(
                    "Users cannot follow themselves"
            );
        }

        this.id = id;
        this.followerId = followerId;
        this.followeeId = followeeId;
        this.createdAt = createdAt;
    }

    public static Follow of(UserId followerId, UserId followeeId) {
        var now = Instant.now();
        var follow = new Follow(UUID.randomUUID(), followerId, followeeId, now);
        follow.registerEvent(new UserFollowedEvent(
                UUID.randomUUID(),
                followerId,
                followeeId,
                now
        ));
        return follow;
    }

    public static Follow rehydrate(
            UUID id,
            UserId followerId,
            UserId followingId,
            Instant createdAt
    ) {
        return new Follow(
                id,
                followerId,
                followingId,
                createdAt
        );
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UserId getFollowerId() {
        return followerId;
    }

    public UserId getFolloweeId() {
        return followeeId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
