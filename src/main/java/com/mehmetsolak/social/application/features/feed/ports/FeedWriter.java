package com.mehmetsolak.social.application.features.feed.ports;

import java.time.Instant;
import java.util.UUID;

public interface FeedWriter {
    void addPost(UUID userId, UUID postId, Instant createdAt);
}
