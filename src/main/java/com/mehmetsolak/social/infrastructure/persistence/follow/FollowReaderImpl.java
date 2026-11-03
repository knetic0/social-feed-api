package com.mehmetsolak.social.infrastructure.persistence.follow;

import com.mehmetsolak.social.application.features.feed.ports.FollowerReader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FollowReaderImpl implements FollowerReader {

    private final FollowJpaRepository repository;

    public FollowReaderImpl(FollowJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UUID> findFollowerIds(UUID followeeId, UUID cursor, int limit) {
        return repository.findFollowerIds(
                followeeId,
                cursor,
                limit
        );
    }
}
