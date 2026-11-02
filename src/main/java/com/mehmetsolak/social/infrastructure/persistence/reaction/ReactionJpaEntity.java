package com.mehmetsolak.social.infrastructure.persistence.reaction;

import com.mehmetsolak.social.domain.post.model.ReactionType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "reactions",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_reactions_user_post",
                        columnNames = {"user_id", "post_id"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_reactions_post_id",
                        columnList = "post_id"
                )
        })
public class ReactionJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "post_id", nullable = false, updatable = false)
    private UUID postId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType type;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    private ReactionJpaEntity() { }

    public ReactionJpaEntity(
            UUID id,
            UUID userId,
            UUID postId,
            ReactionType type,
            Instant createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.type = type;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
