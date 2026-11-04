package com.mehmetsolak.social.application.cqrs;

public interface IdempotentCommand<TResponse> extends Command<TResponse> {
    String idempotencyKey();
}
