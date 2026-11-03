package com.mehmetsolak.social.infrastructure.persistence.follow;

import com.mehmetsolak.social.application.features.feed.ports.FollowerReader;

import java.util.List;
import java.util.UUID;

public class FollowJpaReader implements FollowerReader {

    private final FollowJpaRepository repository;

    public FollowJpaReader(FollowJpaRepository repository) {
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
