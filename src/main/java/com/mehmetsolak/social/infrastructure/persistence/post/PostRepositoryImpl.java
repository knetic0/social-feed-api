package com.mehmetsolak.social.infrastructure.persistence.post;

import com.mehmetsolak.social.domain.post.model.Post;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import org.springframework.stereotype.Component;

@Component
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository repository;
    private final PostMapper mapper;

    public PostRepositoryImpl(PostJpaRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Post post) {
        repository.save(mapper.toEntity(post));
    }
}
