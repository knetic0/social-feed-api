package com.mehmetsolak.social.application.features.post.commands.create;

import com.mehmetsolak.social.application.cqrs.IdempotentCommand;

public record CreatePostCommand(String body, String idempotencyKey)
        implements IdempotentCommand<CreatePostCommandResponse> { }
