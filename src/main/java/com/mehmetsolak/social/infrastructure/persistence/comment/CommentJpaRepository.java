package com.mehmetsolak.social.infrastructure.persistence.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentJpaRepository extends JpaRepository<CommentJpaEntity, UUID> {
}
