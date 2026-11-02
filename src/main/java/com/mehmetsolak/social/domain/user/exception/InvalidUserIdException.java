package com.mehmetsolak.social.domain.user.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class InvalidUserIdException extends DomainException {
    public InvalidUserIdException(String message) {
        super(message);
    }
}
