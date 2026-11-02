package com.mehmetsolak.social.infrastructure.persistence.comment;

import com.mehmetsolak.social.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository repository;

    public CommentRepositoryImpl(CommentJpaRepository repository) {
        this.repository = repository;
    }
}
