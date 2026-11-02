package com.mehmetsolak.social.domain.comment.exception;

import com.mehmetsolak.social.domain.shared.exceptions.DomainValidationException;

public class InvalidCommentBodyException extends DomainValidationException {
    public InvalidCommentBodyException(String message) {
        super(message);
    }
}
