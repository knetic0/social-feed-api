package com.mehmetsolak.social.application.shared.exception;

public class ApplicationConflictException extends RuntimeException {
    public ApplicationConflictException(String message) {
        super(message);
    }
}
