package com.mehmetsolak.social.application.features.auth.commands.register;

import com.mehmetsolak.social.application.shared.exception.ApplicationConflictException;

public class UserAlreadyExistsException extends ApplicationConflictException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
