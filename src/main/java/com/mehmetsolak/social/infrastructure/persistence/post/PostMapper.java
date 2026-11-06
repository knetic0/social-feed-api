package com.mehmetsolak.social.infrastructure.persistence.post;

import com.mehmetsolak.social.domain.post.model.Post;
import com.mehmetsolak.social.domain.post.vobj.PostBody;
import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.user.vobj.UserId;
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

    public Post toDomain(PostJpaEntity entity) {
        return Post.rehydrate(
                PostId.of(entity.getId()),
                UserId.of(entity.getAuthorId()),
                PostBody.of(entity.getBody()),
                entity.getCreatedAt(),
                entity.getDeletedAt(),
                entity.getLikeCount(),
                entity.getCommentCount()
        );
    }
}
