package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.DomainValidationException;

public class InvalidLikeCountException extends DomainValidationException {
    public InvalidLikeCountException(String message) {
        super(message);
    }
}
