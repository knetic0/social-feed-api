package com.mehmetsolak.social.domain.post.repository;

import com.mehmetsolak.social.domain.post.model.Post;

public interface PostRepository {
    void save(Post post);
}
