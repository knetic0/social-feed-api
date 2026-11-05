package com.mehmetsolak.social.application.features.feed.ports;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface PostSummaryReader {
    List<PostSummary> findByIds(List<UUID> postIds);

    record PostSummary(
            UUID postId, UUID authorId, String authorHandle,
            String body, Instant createdAt, long likeCount, long commentCount
    ) {}
}
