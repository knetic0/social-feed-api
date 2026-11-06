package com.mehmetsolak.social.domain.post.exception;

import com.mehmetsolak.social.domain.shared.exceptions.DomainNotAllowedException;

public class PostDeletionNotAllowedException extends DomainNotAllowedException {
    public PostDeletionNotAllowedException(String message) {
        super(message);
    }
}
