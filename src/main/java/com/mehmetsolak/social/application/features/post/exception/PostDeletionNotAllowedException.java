package com.mehmetsolak.social.application.features.post.exception;

import com.mehmetsolak.social.application.shared.exception.NotAllowedException;

public class PostDeletionNotAllowedException extends NotAllowedException {
    public PostDeletionNotAllowedException(String message) {
        super(message);
    }
}
