package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.DomainValidationException;

public class InvalidCommentCountException extends DomainValidationException {
    public InvalidCommentCountException(String message) {
        super(message);
    }
}
