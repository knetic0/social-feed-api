package com.mehmetsolak.social.domain.user.model;

import com.mehmetsolak.social.domain.shared.AggregateRoot;
import com.mehmetsolak.social.domain.user.vobj.Handle;
import com.mehmetsolak.social.domain.user.vobj.PasswordHash;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.time.Instant;

public final class User extends AggregateRoot<UserId> {

    private final UserId id;
    private final Handle handle;
    private final PasswordHash passwordHash;
    private final boolean isCelebrity;
    private final Instant createdAt;

    private User(
            UserId id,
            Handle handle,
            PasswordHash passwordHash,
            boolean isCelebrity,
            Instant createdAt
    ) {
        this.id = id;
        this.handle = handle;
        this.passwordHash = passwordHash;
        this.isCelebrity = isCelebrity;
        this.createdAt = createdAt;
    }

    public static User of(Handle handle, PasswordHash passwordHash, boolean isCelebrity) {
        var id = UserId.newId();
        var createdAt = Instant.now();
        return new User(id, handle, passwordHash, isCelebrity, createdAt);
    }

    public static User rehydrate(
            UserId id,
            Handle handle,
            PasswordHash passwordHash,
            boolean isCelebrity,
            Instant createdAt
    ) {
        return new User(
                id,
                handle,
                passwordHash,
                isCelebrity,
                createdAt
        );
    }

    @Override
    public UserId getId() {
        return id;
    }

    public Handle getHandle() {
        return handle;
    }

    public PasswordHash getPasswordHash() { return passwordHash; }

    public boolean isCelebrity() {
        return isCelebrity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
