package com.mehmetsolak.social.domain.post.repository;

import com.mehmetsolak.social.domain.post.model.Post;
import com.mehmetsolak.social.domain.post.vobj.PostId;

import java.util.Optional;

public interface PostRepository {
    void save(Post post);
    Optional<Post> findById(PostId postId);
}
