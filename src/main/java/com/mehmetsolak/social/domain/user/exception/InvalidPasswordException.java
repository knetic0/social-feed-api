package com.mehmetsolak.social.domain.user.exception;

import com.mehmetsolak.social.domain.shared.DomainValidationException;

public class InvalidPasswordException extends DomainValidationException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
