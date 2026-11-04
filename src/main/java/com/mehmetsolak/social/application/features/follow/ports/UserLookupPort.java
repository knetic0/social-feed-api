package com.mehmetsolak.social.application.features.follow.ports;

import com.mehmetsolak.social.domain.user.vobj.UserId;

public interface UserLookupPort {
    boolean exists(UserId userId);
}
