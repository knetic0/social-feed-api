package com.mehmetsolak.social.infrastructure.persistence.user;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_users_handle",
                        columnNames = "handle"
                )
        })
public class UserJpaEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String handle;

    @Column(nullable = false, name = "password_hash", length = 255)
    private String passwordHash;

    @Column(nullable = false, name = "is_celebrity")
    private boolean isCelebrity;

    @Column(nullable = false, updatable = false, name = "created_at")
    private Instant createdAt;

    private UserJpaEntity() { }

    public UserJpaEntity(
            UUID id,
            String handle,
            String passwordHash,
            boolean isCelebrity,
            Instant createdAt
    ) {
        this.id = id;
        this.handle = handle;
        this.passwordHash = passwordHash;
        this.isCelebrity = isCelebrity;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isCelebrity() {
        return isCelebrity;
    }

    public void setCelebrity(boolean celebrity) {
        isCelebrity = celebrity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
