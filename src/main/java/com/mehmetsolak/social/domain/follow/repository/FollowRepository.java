package com.mehmetsolak.social.domain.follow.repository;

import com.mehmetsolak.social.domain.follow.model.Follow;

public interface FollowRepository {
    boolean insertIfAbsent(Follow follow);
}
