package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.DomainConflictException;

public class PostAlreadyDeletedException extends DomainConflictException {
    public PostAlreadyDeletedException(String message) {
        super(message);
    }
}
