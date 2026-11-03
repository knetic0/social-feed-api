package com.mehmetsolak.social.application.features.feed.commands.distribute;

import com.mehmetsolak.social.application.cqrs.Command;

import java.time.Instant;
import java.util.UUID;

public record DistributePostCommand(
        UUID eventId, UUID postId, UUID authorId, Instant occurredAt) implements Command<Void> { }
