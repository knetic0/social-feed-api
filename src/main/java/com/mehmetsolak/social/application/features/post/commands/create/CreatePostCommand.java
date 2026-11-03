package com.mehmetsolak.social.application.features.post.commands.create;

import com.mehmetsolak.social.application.cqrs.Command;

import java.util.UUID;

public record CreatePostCommand(
        String body, UUID idempotencyKey) implements Command<CreatePostCommandResponse> { }
