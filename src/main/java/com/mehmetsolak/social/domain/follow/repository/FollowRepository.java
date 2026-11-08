package com.mehmetsolak.social.domain.follow.repository;

import com.mehmetsolak.social.domain.follow.model.Follow;
import com.mehmetsolak.social.domain.user.vobj.UserId;

public interface FollowRepository {
    boolean insertIfAbsent(Follow follow);
    boolean deleteIfExists(UserId followerId, UserId followeeId);
}
