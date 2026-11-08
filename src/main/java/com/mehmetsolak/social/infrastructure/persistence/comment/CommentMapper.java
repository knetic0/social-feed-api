package com.mehmetsolak.social.infrastructure.persistence.comment;

import com.mehmetsolak.social.domain.comment.model.Comment;
import org.springframework.stereotype.Component;

@Component
public final class CommentMapper {

    public CommentJpaEntity toEntity(Comment comment) {
        return new CommentJpaEntity(
                comment.getId(),
                comment.getUserId().value(),
                comment.getPostId().value(),
                comment.getBody().value(),
                comment.getCreatedAt()
        );
    }
}
