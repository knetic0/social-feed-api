package com.mehmetsolak.social.domain.follow.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class CannotFollowYourselfException extends DomainException {
    public CannotFollowYourselfException(String message) {
        super(message);
    }
}
