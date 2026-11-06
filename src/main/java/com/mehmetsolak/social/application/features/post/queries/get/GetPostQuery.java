package com.mehmetsolak.social.application.features.post.queries.get;

import com.mehmetsolak.social.application.cqrs.Query;

import java.util.UUID;

public record GetPostQuery(UUID postId)
    implements Query<GetPostQueryResponse> { }
