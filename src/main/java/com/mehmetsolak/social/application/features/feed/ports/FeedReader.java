package com.mehmetsolak.social.application.features.feed.ports;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface FeedReader {
    List<FeedEntry> readFeed(UUID userId, Long cursor, int limit);
    record FeedEntry(UUID postId, Instant createdAt) {}
}
