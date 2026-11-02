package com.mehmetsolak.social.domain.user.exception;

import com.mehmetsolak.social.domain.shared.exceptions.DomainValidationException;

public class InvalidPasswordException extends DomainValidationException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
