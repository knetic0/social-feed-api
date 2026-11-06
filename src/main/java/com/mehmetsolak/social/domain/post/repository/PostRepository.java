package com.mehmetsolak.social.domain.post.repository;

import com.mehmetsolak.social.domain.post.model.Post;
import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.util.Optional;

public interface PostRepository {
    void save(Post post);
    Optional<Post> findById(PostId postId);
}
