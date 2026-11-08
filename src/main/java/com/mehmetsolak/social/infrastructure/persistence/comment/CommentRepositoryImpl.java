package com.mehmetsolak.social.infrastructure.persistence.comment;

import com.mehmetsolak.social.domain.comment.model.Comment;
import com.mehmetsolak.social.domain.comment.repository.CommentRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository repository;
    private final CommentMapper mapper;

    public CommentRepositoryImpl(CommentJpaRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Comment comment) {
        repository.save(mapper.toEntity(comment));
    }
}
