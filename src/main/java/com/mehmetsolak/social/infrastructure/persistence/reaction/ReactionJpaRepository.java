package com.mehmetsolak.social.infrastructure.persistence.reaction;

import com.mehmetsolak.social.domain.reaction.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReactionJpaRepository extends JpaRepository<Reaction, UUID> {
}
