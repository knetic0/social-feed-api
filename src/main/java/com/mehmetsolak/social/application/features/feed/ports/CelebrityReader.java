package com.mehmetsolak.social.application.features.feed.ports;

import java.util.List;
import java.util.UUID;

public interface CelebrityReader {
    List<UUID> findCelebrityFolloweeIds(UUID followerId, UUID cursor, int limit);
}
