package com.mehmetsolak.social.application.features.post.exception;

import com.mehmetsolak.social.application.shared.exception.NotFoundException;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
