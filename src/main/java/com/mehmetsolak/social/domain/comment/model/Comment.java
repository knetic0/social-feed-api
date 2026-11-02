package com.mehmetsolak.social.domain.comment.model;

import com.mehmetsolak.social.domain.comment.exception.InvalidCommentBodyException;
import com.mehmetsolak.social.domain.comment.vobj.CommentBody;
import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.shared.AggregateRoot;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.time.Instant;
import java.util.UUID;

public final class Comment extends AggregateRoot<UUID> {

    private final UUID id;
    private final UserId userId;
    private final PostId postId;
    private CommentBody body;
    private final Instant createdAt;

    private Comment(
            UUID id,
            UserId userId,
            PostId postId,
            CommentBody body,
            Instant createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.body = body;
        this.createdAt = createdAt;
    }

    public static Comment of(UserId userId, PostId postId, CommentBody body){
        return new Comment(
                UUID.randomUUID(),
                userId,
                postId,
                body,
                Instant.now()
        );
    }

    public static Comment rehydrate(
            UUID id,
            UserId userId,
            PostId postId,
            CommentBody body,
            Instant createdAt
    ) {
        return new Comment(
                id,
                userId,
                postId,
                body,
                createdAt
        );
    }

    public void edit(CommentBody body) {
        if (body == null) {
            throw new InvalidCommentBodyException(
                    "Comment body cannot be null"
            );
        }

        if (this.body.equals(body)) {
            return;
        }

        this.body = body;
    }

    public boolean isOwnedBy(UserId userId) {
        return this.userId.equals(userId);
    }

    public boolean canBeEditedBy(UserId userId) {
        return isOwnedBy(userId);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UserId getUserId() {
        return this.userId;
    }

    public PostId getPostId() {
        return this.postId;
    }

    public CommentBody getBody() {
        return this.body;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }
}
