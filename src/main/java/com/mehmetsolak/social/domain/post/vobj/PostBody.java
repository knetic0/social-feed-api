package com.mehmetsolak.social.domain.post.vobj;

import com.mehmetsolak.social.domain.post.exception.InvalidPostBodyException;

public record PostBody(String value) {

    private static final int MAX_LENGTH = 280;

    public PostBody {
        if (value == null) {
            throw new InvalidPostBodyException("Body cannot be null");
        }

        value = value.trim();

        if (value.codePointCount(0, value.length()) > MAX_LENGTH) {
            throw new InvalidPostBodyException(
                    "Post body cannot exceed %d characters".formatted(MAX_LENGTH)
            );
        }
    }

    public static PostBody of(String value) {
        return new PostBody(value);
    }
}
