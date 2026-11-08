package com.mehmetsolak.social.application.features.comment.commands.create;

import com.mehmetsolak.social.application.cqrs.IdempotentCommand;

import java.util.UUID;

public record CreateCommentCommand(UUID userId, UUID postId, String body, String idempotencyKey)
        implements IdempotentCommand<CreateCommentCommandResponse> { }
