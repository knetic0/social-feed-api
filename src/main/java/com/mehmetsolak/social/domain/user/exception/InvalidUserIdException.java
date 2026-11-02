package com.mehmetsolak.social.domain.user.exception;

import com.mehmetsolak.social.domain.shared.exceptions.DomainValidationException;

public class InvalidUserIdException extends DomainValidationException {
    public InvalidUserIdException(String message) {
        super(message);
    }
}
