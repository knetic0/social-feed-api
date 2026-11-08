package com.mehmetsolak.social.infrastructure.persistence.reaction;

import com.mehmetsolak.social.domain.reaction.model.Reaction;
import org.springframework.stereotype.Component;

@Component
public final class ReactionMapper {

    public ReactionJpaEntity toEntity(Reaction reaction) {
        return new ReactionJpaEntity(
                reaction.getId(),
                reaction.getUserId().value(),
                reaction.getPostId().value(),
                reaction.getCreatedAt()
        );
    }
}
