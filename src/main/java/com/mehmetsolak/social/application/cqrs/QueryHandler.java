package com.mehmetsolak.social.application.cqrs;

public interface QueryHandler<TQuery extends Query<TResponse>, TResponse> {
    TResponse execute(TQuery query);
}
