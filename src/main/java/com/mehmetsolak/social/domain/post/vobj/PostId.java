package com.mehmetsolak.social.domain.post.vobj;


import com.mehmetsolak.social.domain.post.exception.InvalidPostIdException;

import java.util.UUID;

public record PostId(UUID value) {

    public PostId {
        if (value == null) {
            throw new InvalidPostIdException("User id cannot be null");
        }
    }

    public static PostId newId() {
        return new PostId(UUID.randomUUID());
    }

    public static PostId of(UUID value) {
        return new PostId(value);
    }
}
