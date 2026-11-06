package com.mehmetsolak.social.application.features.post.queries.list;

import com.mehmetsolak.social.application.cqrs.Query;

import java.util.UUID;

public record GetPostsQuery(UUID authorId) implements Query<GetPostsQueryResponse> {
}
