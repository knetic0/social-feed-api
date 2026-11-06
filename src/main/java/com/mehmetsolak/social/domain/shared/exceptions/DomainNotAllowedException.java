package com.mehmetsolak.social.domain.shared.exceptions;

public class DomainNotAllowedException extends RuntimeException {
    public DomainNotAllowedException(String message) {
        super(message);
    }
}
