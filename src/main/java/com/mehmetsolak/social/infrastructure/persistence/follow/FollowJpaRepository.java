package com.mehmetsolak.social.infrastructure.persistence.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FollowJpaRepository extends JpaRepository<FollowJpaEntity, UUID> {
}
