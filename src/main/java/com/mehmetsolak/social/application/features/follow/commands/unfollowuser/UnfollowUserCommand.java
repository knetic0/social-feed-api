package com.mehmetsolak.social.application.features.follow.commands.unfollowuser;

import com.mehmetsolak.social.application.cqrs.Command;

import java.util.UUID;

public record UnfollowUserCommand(UUID followerId, UUID followeeId) implements Command<Void> { }
