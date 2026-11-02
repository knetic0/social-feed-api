package com.mehmetsolak.social.infrastructure.persistence.follow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "follows")
public class FollowJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "follower_id", nullable = false, updatable = false)
    private UUID followerId;

    @Column(name = "followee_id", nullable = false, updatable = false)
    private UUID followeeId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    private FollowJpaEntity() {}

    private FollowJpaEntity(
            UUID id,
            UUID followerId,
            UUID followeeId,
            Instant createdAt
    ) {
        this.id = id;
        this.followerId = followerId;
        this.followeeId = followeeId;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFollowerId() {
        return followerId;
    }

    public void setFollowerId(UUID followerId) {
        this.followerId = followerId;
    }

    public UUID getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(UUID followeeId) {
        this.followeeId = followeeId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
