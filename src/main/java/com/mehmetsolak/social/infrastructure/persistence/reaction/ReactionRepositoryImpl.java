package com.mehmetsolak.social.infrastructure.persistence.reaction;

import com.mehmetsolak.social.domain.reaction.repository.ReactionRepository;
import org.springframework.stereotype.Component;

@Component
public class ReactionRepositoryImpl implements ReactionRepository {

    private final ReactionJpaRepository repository;

    public ReactionRepositoryImpl(ReactionJpaRepository repository) {
        this.repository = repository;
    }
}
