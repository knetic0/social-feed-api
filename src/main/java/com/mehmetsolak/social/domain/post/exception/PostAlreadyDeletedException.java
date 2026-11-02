package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.DomainException;

public class PostAlreadyDeletedException extends DomainException {
    public PostAlreadyDeletedException(String message) {
        super(message);
    }
}
