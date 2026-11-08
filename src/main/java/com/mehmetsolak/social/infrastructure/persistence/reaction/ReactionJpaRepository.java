package com.mehmetsolak.social.infrastructure.persistence.reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReactionJpaRepository extends JpaRepository<ReactionJpaEntity, UUID> {
    void deleteByUserIdAndPostId(UUID userId, UUID postId);
}
