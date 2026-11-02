package com.mehmetsolak.social.domain.post.model;

import com.mehmetsolak.social.domain.post.events.PostCreatedEvent;
import com.mehmetsolak.social.domain.post.exception.InvalidCommentCountException;
import com.mehmetsolak.social.domain.post.exception.InvalidLikeCountException;
import com.mehmetsolak.social.domain.post.exception.PostAlreadyDeletedException;
import com.mehmetsolak.social.domain.post.vobj.PostBody;
import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.shared.AggregateRoot;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.time.Instant;
import java.util.UUID;

public final class Post extends AggregateRoot<PostId> {

    private final PostId id;
    private final UserId authorId;
    private PostBody body;
    private final Instant createdAt;
    private Instant deletedAt;
    private long likeCount;
    private long commentCount;

    private Post(
            PostId id,
            UserId authorId,
            PostBody body,
            Instant createdAt
    ) {
        this.id = id;
        this.authorId = authorId;
        this.body = body;
        this.createdAt = createdAt;
    }

    private Post(
            PostId id,
            UserId authorId,
            PostBody body,
            Instant createdAt,
            Instant deletedAt,
            long likeCount,
            long commentCount
    ) {
        this(id, authorId, body, createdAt);
        this.deletedAt = deletedAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public static Post of(UserId authorId, PostBody body) {
        var post = new Post(PostId.newId(), authorId, body, Instant.now());
        post.registerEvent(new PostCreatedEvent(
                UUID.randomUUID(),
                post.getId().value(),
                authorId.value(),
                post.getCreatedAt()
        ));
        return post;
    }

    public static Post rehydrate(
            PostId id,
            UserId authorId,
            PostBody body,
            Instant createdAt,
            Instant deletedAt,
            long likeCount,
            long commentCount
    ) {
        return new Post(
                id,
                authorId,
                body,
                createdAt,
                deletedAt,
                likeCount,
                commentCount
        );
    }

    public void incrementLikeCount() {
        ensureNotDeleted();
        likeCount++;
    }

    public void decrementLikeCount() {
        ensureNotDeleted();

        if (likeCount <= 0) {
            throw new InvalidLikeCountException("Like count cannot be negative");
        }

        likeCount--;
    }

    public void incrementCommentCount() {
        ensureNotDeleted();
        commentCount++;
    }

    public void decrementCommentCount() {
        ensureNotDeleted();

        if (commentCount <= 0) {
            throw new InvalidCommentCountException("Like count cannot be negative");
        }

        commentCount--;
    }

    public void delete() {
        ensureNotDeleted();
        deletedAt = Instant.now();
    }

    private void ensureNotDeleted() {
        if (deletedAt != null) {
            throw new PostAlreadyDeletedException(
                    "Post already deleted"
            );
        }
    }

    @Override
    public PostId getId() {
        return id;
    }

    public UserId getAuthorId() {
        return this.authorId;
    }

    public PostBody getBody() {
        return this.body;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getDeletedAt() {
        return this.deletedAt;
    }

    public long getLikeCount() {
        return this.likeCount;
    }

    public long getCommentCount() {
        return this.commentCount;
    }
}
