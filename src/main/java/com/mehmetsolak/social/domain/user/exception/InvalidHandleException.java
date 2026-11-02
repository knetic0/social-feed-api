package com.mehmetsolak.social.domain.user.exception;

import com.mehmetsolak.social.domain.shared.DomainValidationException;

public class InvalidHandleException extends DomainValidationException {
    public InvalidHandleException(String message) {
        super(message);
    }
}
