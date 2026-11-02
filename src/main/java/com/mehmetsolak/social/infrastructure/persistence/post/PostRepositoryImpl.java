package com.mehmetsolak.social.infrastructure.persistence.post;

import com.mehmetsolak.social.domain.post.repository.PostRepository;
import org.springframework.stereotype.Component;

@Component
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository repository;

    public PostRepositoryImpl(PostJpaRepository repository) {
        this.repository = repository;
    }
}
