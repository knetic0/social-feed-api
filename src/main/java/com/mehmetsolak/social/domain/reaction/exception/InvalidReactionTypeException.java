package com.mehmetsolak.social.domain.reaction.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class InvalidReactionTypeException extends DomainException {
    public InvalidReactionTypeException(String message) {
        super(message);
    }
}
