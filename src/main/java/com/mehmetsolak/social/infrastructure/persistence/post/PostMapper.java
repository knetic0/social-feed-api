package com.mehmetsolak.social.infrastructure.persistence.post;

import com.mehmetsolak.social.domain.post.model.Post;
import org.springframework.stereotype.Component;

@Component
public final class PostMapper {

    public PostJpaEntity toEntity(Post post) {
        return new PostJpaEntity(
                post.getId().value(),
                post.getAuthorId().value(),
                post.getBody().value(),
                post.getCreatedAt(),
                post.getDeletedAt(),
                post.getLikeCount(),
                post.getCommentCount()
        );
    }
}
