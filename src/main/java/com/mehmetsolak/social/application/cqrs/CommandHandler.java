package com.mehmetsolak.social.application.cqrs;

public interface CommandHandler<TCommand extends Command<TResponse>, TResponse> {
    TResponse execute(TCommand command);
}
