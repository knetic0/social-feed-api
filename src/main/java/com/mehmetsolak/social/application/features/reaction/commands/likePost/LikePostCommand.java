package com.mehmetsolak.social.application.features.reaction.commands.likePost;

import com.mehmetsolak.social.application.cqrs.Command;

import java.util.UUID;

public record LikePostCommand(UUID userId, UUID postId) implements Command<Void> {
}
