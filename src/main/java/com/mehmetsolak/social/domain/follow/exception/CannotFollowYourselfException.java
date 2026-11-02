package com.mehmetsolak.social.domain.follow.exception;

import com.mehmetsolak.social.domain.shared.DomainValidationException;

public class CannotFollowYourselfException extends DomainValidationException {
    public CannotFollowYourselfException(String message) {
        super(message);
    }
}
