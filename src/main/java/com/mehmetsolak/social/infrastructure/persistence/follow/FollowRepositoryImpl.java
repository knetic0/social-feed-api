package com.mehmetsolak.social.infrastructure.persistence.follow;

import com.mehmetsolak.social.domain.follow.model.Follow;
import com.mehmetsolak.social.domain.follow.repository.FollowRepository;
import com.mehmetsolak.social.domain.user.vobj.UserId;
import org.springframework.stereotype.Component;

@Component
public class FollowRepositoryImpl implements FollowRepository {

    private final FollowJpaRepository repository;

    public FollowRepositoryImpl(FollowJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean insertIfAbsent(Follow follow) {
        return repository.insertIfAbsent(
                follow.getId(),
                follow.getFollowerId().value(),
                follow.getFolloweeId().value(),
                follow.getCreatedAt()) == 1;
    }

    @Override
    public boolean deleteIfExists(UserId followerId, UserId followeeId) {
        return repository.deleteIfExists(
                followerId.value(),
                followeeId.value()) == 1;
    }
}
