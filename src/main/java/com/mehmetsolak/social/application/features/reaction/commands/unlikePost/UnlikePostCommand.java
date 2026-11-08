package com.mehmetsolak.social.application.features.reaction.commands.unlikePost;

import com.mehmetsolak.social.application.cqrs.Command;

import java.util.UUID;

public record UnlikePostCommand(UUID userId, UUID postId) implements Command<Void> {
}
