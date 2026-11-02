package com.mehmetsolak.social.domain.reaction.exception;

import com.mehmetsolak.social.domain.shared.DomainValidationException;

public class InvalidReactionTypeException extends DomainValidationException {
    public InvalidReactionTypeException(String message) {
        super(message);
    }
}
