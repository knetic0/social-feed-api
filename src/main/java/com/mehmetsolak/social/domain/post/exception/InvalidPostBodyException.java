package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.DomainValidationException;

public class InvalidPostBodyException extends DomainValidationException {
    public InvalidPostBodyException(String message) {
        super(message);
    }
}
