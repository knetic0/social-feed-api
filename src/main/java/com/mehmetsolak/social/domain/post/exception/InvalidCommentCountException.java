package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.exceptions.DomainValidationException;

public class InvalidCommentCountException extends DomainValidationException {
    public InvalidCommentCountException(String message) {
        super(message);
    }
}
