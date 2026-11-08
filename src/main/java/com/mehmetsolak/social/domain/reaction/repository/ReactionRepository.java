package com.mehmetsolak.social.domain.reaction.repository;

import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.reaction.model.Reaction;
import com.mehmetsolak.social.domain.user.vobj.UserId;

public interface ReactionRepository {
    void save(Reaction reaction);
    void deleteByUserIdAndPostId(UserId userId, PostId postId);
}
