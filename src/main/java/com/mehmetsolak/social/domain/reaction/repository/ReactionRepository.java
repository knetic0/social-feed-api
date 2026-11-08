package com.mehmetsolak.social.domain.reaction.repository;

import com.mehmetsolak.social.domain.reaction.model.Reaction;

public interface ReactionRepository {
    void save(Reaction reaction);
}
