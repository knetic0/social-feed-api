package com.mehmetsolak.social.infrastructure.persistence.follow;

import com.mehmetsolak.social.application.features.feed.ports.CelebrityReader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class JpaCelebrityReaderImpl implements CelebrityReader {

    private final FollowJpaRepository repository;

    public JpaCelebrityReaderImpl(FollowJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UUID> findCelebrityFolloweeIds(UUID followerId, UUID cursor, int limit) {
        return repository.findCelebrityFolloweeIds(followerId, cursor, limit);
    }
}
