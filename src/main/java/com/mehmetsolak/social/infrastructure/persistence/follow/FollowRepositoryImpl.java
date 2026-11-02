package com.mehmetsolak.social.infrastructure.persistence.follow;

import com.mehmetsolak.social.domain.follow.repository.FollowRepository;
import org.springframework.stereotype.Component;

@Component
public class FollowRepositoryImpl implements FollowRepository {

    private final FollowJpaRepository repository;

    public FollowRepositoryImpl(FollowJpaRepository repository) {
        this.repository = repository;
    }
}
