package com.mehmetsolak.social.application.features.feed.ports;

import java.util.List;
import java.util.UUID;

public interface FollowerReader {
    List<UUID> findFollowerIds(UUID followeeId, UUID cursor, int limit);
}
