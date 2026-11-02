package com.mehmetsolak.social.infrastructure.persistence.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostJpaRepository extends JpaRepository<PostJpaEntity, UUID> {
}
