package com.mehmetsolak.social.domain.comment.vobj;

import com.mehmetsolak.social.domain.comment.exception.InvalidCommentBodyException;

public record CommentBody(String value) {

    private static final int MAX_LENGTH = 150;

    public CommentBody {
        if (value == null) {
            throw new InvalidCommentBodyException("Comment body cannot be null");
        }

        value = value.trim();

        if (value.codePointCount(0, value.length()) > MAX_LENGTH) {
            throw new InvalidCommentBodyException(
                    "Comment body cannot exceed %d characters".formatted(MAX_LENGTH)
            );
        }
    }

    public static CommentBody of(String value) {
        return new CommentBody(value);
    }
}
