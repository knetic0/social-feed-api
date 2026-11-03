package com.mehmetsolak.social.infrastructure.cache;

import com.mehmetsolak.social.application.features.feed.ports.FeedWriter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class RedisFeedWriter implements FeedWriter {

    private final StringRedisTemplate redisTemplate;

    public RedisFeedWriter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void addPost(UUID userId, UUID postId, Instant createdAt) {
        String key = "feed:user:" + userId;

        double score = createdAt.toEpochMilli();

        redisTemplate.opsForZSet().add(
                key,
                postId.toString(),
                score
        );
    }
}
