package com.mehmetsolak.social.application.features.auth.commands.register;

import com.mehmetsolak.social.application.cqrs.Command;

public record RegisterCommand(String handle, String password)
        implements Command<Void> { }
