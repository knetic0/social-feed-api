package com.mehmetsolak.social.application.features.follow.commands.followuser;

import com.mehmetsolak.social.application.cqrs.Command;
import com.mehmetsolak.social.domain.user.vobj.UserId;

public record FollowUserCommand(UserId followerId, UserId followeeId)
        implements Command<Void> { }
