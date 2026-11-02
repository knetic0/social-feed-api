package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class InvalidLikeCountException extends DomainException {
    public InvalidLikeCountException(String message) {
        super(message);
    }
}
