package com.mehmetsolak.social.domain.user.vobj;

import com.mehmetsolak.social.domain.user.exception.InvalidUserIdException;

import java.util.UUID;

public record UserId(UUID value) {

    public UserId {
        if (value == null) {
            throw new InvalidUserIdException("User id cannot be null");
        }
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId of(UUID value) {
        return new UserId(value);
    }
}
