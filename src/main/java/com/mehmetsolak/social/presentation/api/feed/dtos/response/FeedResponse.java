package com.mehmetsolak.social.presentation.api.feed.dtos.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record FeedResponse(List<FeedItemResponse> items, Long nextCursor) {

    public record FeedItemResponse(
            UUID postId,
            UUID authorId,
            String authorHandle,
            String body,
            Instant createdAt,
            long likeCount,
            long commentCount
    ) {}
}
