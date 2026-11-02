package com.mehmetsolak.social.application.features.post.commands.create;

import com.mehmetsolak.social.application.cqrs.Command;

public record CreatePostCommand(String body) implements Command<CreatePostCommandResponse> { }
