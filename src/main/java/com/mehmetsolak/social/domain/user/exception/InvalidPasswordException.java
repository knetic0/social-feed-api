package com.mehmetsolak.social.domain.user.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class InvalidPasswordException extends DomainException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
