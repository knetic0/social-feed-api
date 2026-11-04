package com.mehmetsolak.social.application.shared.exception;

import java.util.UUID;

public class IdempotencyKeyReusedException extends RuntimeException {
    public IdempotencyKeyReusedException(UUID key) {
        super("Idempotency key '" + key + "' was already used with a different request payload.");
    }
}
