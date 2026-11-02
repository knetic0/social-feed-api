package com.mehmetsolak.social.domain.reaction.model;

import com.mehmetsolak.social.domain.post.model.ReactionType;
import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.reaction.exception.InvalidReactionTypeException;
import com.mehmetsolak.social.domain.shared.AggregateRoot;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.time.Instant;
import java.util.UUID;

public final class Reaction extends AggregateRoot<UUID> {

    private final UUID id;
    private final UserId userId;
    private final PostId postId;
    private ReactionType type;
    private final Instant createdAt;

    private Reaction(
            UUID id,
            UserId userId,
            PostId postId,
            ReactionType type,
            Instant createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static Reaction of(UserId userId, PostId postId, ReactionType type) {
        return new Reaction(UUID.randomUUID(), userId, postId, type, Instant.now());
    }

    public static Reaction rehydrate(
            UUID id,
            UserId userId,
            PostId postId,
            ReactionType type,
            Instant createdAt
    ) {
        return new Reaction(
                id,
                userId,
                postId,
                type,
                createdAt
        );
    }

    public void changeType(ReactionType type) {
        if (type == null) {
            throw new InvalidReactionTypeException(
                    "Reaction type cannot be null"
            );
        }

        this.type = type;
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

    public ReactionType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
