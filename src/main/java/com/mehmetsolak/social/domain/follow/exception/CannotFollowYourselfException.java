package com.mehmetsolak.social.domain.follow.exception;

import com.mehmetsolak.social.domain.shared.exceptions.DomainValidationException;

public class CannotFollowYourselfException extends DomainValidationException {
    public CannotFollowYourselfException(String message) {
        super(message);
    }
}
