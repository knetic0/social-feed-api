package com.mehmetsolak.social.application.shared.exception;

import java.util.UUID;

public class IdempotencyRequestInProgressException extends RuntimeException {
    public IdempotencyRequestInProgressException(UUID key, Throwable cause) {
        super("A request with idempotency key '" + key + "' is still being processed.", cause);
    }
}
