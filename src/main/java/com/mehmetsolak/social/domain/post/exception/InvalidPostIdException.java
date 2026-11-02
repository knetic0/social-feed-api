package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class InvalidPostIdException extends DomainException {
    public InvalidPostIdException(String message) {
        super(message);
    }
}
