package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.DomainValidationException;

public class InvalidPostIdException extends DomainValidationException {
    public InvalidPostIdException(String message) {
        super(message);
    }
}
