package com.mehmetsolak.social.domain.comment.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class InvalidCommentBodyException extends DomainException {
    public InvalidCommentBodyException(String message) {
        super(message);
    }
}
