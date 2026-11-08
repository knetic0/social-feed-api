package com.mehmetsolak.social.infrastructure.persistence.reaction;

import com.mehmetsolak.social.domain.reaction.model.Reaction;
import com.mehmetsolak.social.domain.reaction.repository.ReactionRepository;
import org.springframework.stereotype.Component;

@Component
public class ReactionRepositoryImpl implements ReactionRepository {

    private final ReactionJpaRepository repository;
    private final ReactionMapper mapper;

    public ReactionRepositoryImpl(ReactionJpaRepository repository, ReactionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Reaction reaction) {
        repository.save(mapper.toEntity(reaction));
    }
}
