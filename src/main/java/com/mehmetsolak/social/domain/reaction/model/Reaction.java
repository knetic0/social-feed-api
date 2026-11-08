package com.mehmetsolak.social.domain.reaction.model;

import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.shared.AggregateRoot;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.time.Instant;
import java.util.UUID;

public final class Reaction extends AggregateRoot<UUID> {

    private final UUID id;
    private final UserId userId;
    private final PostId postId;
    private final Instant createdAt;

    private Reaction(
            UUID id,
            UserId userId,
            PostId postId,
            Instant createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.createdAt = createdAt;
    }

    public static Reaction of(UserId userId, PostId postId) {
        return new Reaction(UUID.randomUUID(), userId, postId, Instant.now());
    }

    public static Reaction rehydrate(
            UUID id,
            UserId userId,
            PostId postId,
            Instant createdAt
    ) {
        return new Reaction(
                id,
                userId,
                postId,
                createdAt
        );
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public PostId getPostId() {
        return postId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
