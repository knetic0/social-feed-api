package com.mehmetsolak.social.infrastructure.cache;

import com.mehmetsolak.social.application.features.feed.ports.FeedReader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Component
public class RedisFeedReader implements FeedReader {

    private final StringRedisTemplate redisTemplate;

    public RedisFeedReader(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<FeedEntry> readFeed(UUID userId, Long cursor, int limit) {
        String key = "feed:user:" + userId;
        double max = cursor != null ? cursor - 1 : Double.POSITIVE_INFINITY;

        Set<ZSetOperations.TypedTuple<String>> tuples =
                redisTemplate.opsForZSet()
                        .reverseRangeByScoreWithScores(
                                key, Double.NEGATIVE_INFINITY, max, 0, limit
                        );

        if (tuples == null) return List.of();

        return tuples.stream()
                .map(t -> new FeedEntry(
                        UUID.fromString(Objects.requireNonNull(t.getValue())),
                        Instant.ofEpochMilli(Objects.requireNonNull(t.getScore()).longValue())
                ))
                .toList();
    }
}
