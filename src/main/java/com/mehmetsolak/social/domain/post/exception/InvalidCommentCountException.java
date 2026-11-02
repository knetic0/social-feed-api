package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class InvalidCommentCountException extends DomainException {
    public InvalidCommentCountException(String message) {
        super(message);
    }
}
