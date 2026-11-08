package com.mehmetsolak.social.domain.comment.repository;

import com.mehmetsolak.social.domain.comment.model.Comment;

public interface CommentRepository {
    void save(Comment comment);
}
