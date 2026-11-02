package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class InvalidPostBodyException extends DomainException {
    public InvalidPostBodyException(String message) {
        super(message);
    }
}
