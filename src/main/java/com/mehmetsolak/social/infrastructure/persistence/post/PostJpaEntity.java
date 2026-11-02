package com.mehmetsolak.social.infrastructure.persistence.post;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "posts",
        indexes = {
                @Index(
                        name = "idx_posts_author_created",
                        columnList = "author_id, created_at DESC, id DESC"
                ),
                @Index(
                        name = "idx_posts_created",
                        columnList = "created_at DESC, id DESC"
                )
        })
public class PostJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "author_id", nullable = false, updatable = false)
    private UUID authorId;

    @Column(name = "body", nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "like_count", nullable = false)
    private long likeCount;

    @Column(name = "comment_count", nullable = false)
    private long commentCount;

    private PostJpaEntity() {}

    public PostJpaEntity(
            UUID id,
            UUID authorId,
            String body,
            Instant createdAt,
            Instant deletedAt,
            long likeCount,
            long commentCount
    ) {
        this.id = id;
        this.authorId = authorId;
        this.body = body;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }
}
