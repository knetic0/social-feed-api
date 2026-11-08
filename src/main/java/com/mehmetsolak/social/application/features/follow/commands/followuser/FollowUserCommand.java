package com.mehmetsolak.social.application.features.follow.commands.followuser;

import com.mehmetsolak.social.application.cqrs.Command;

import java.util.UUID;

public record FollowUserCommand(UUID followerId, UUID followeeId)
        implements Command<Void> { }
