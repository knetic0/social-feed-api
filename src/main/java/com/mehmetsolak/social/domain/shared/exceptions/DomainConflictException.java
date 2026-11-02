package com.mehmetsolak.social.domain.shared.exceptions;

public class DomainConflictException extends RuntimeException {
    public DomainConflictException(String message) {
        super(message);
    }
}
