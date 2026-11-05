package com.mehmetsolak.social.application.features.feed.queries.get;

import com.mehmetsolak.social.application.cqrs.Query;

import java.util.UUID;

public record GetFeedQuery(UUID userId, int size, Long cursor)
        implements Query<GetFeedQueryResponse> { }
