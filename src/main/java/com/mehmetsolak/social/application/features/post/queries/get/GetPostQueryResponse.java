package com.mehmetsolak.social.application.features.post.queries.get;

import java.time.Instant;
import java.util.UUID;

public record GetPostQueryResponse(
        UUID id,
        UUID authorId,
        String body,
        Instant createdAt,
        long likeCount,
        long commentCount
) { }
