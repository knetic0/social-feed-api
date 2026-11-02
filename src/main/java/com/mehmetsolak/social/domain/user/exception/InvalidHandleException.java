package com.mehmetsolak.social.domain.user.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class InvalidHandleException extends DomainException {
    public InvalidHandleException(String message) {
        super(message);
    }
}
