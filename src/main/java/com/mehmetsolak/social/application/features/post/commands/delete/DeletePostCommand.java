package com.mehmetsolak.social.application.features.post.commands.delete;

import com.mehmetsolak.social.application.cqrs.Command;

import java.util.UUID;

public record DeletePostCommand(UUID userId, UUID postId)
    implements Command<Void> { }
