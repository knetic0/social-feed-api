package com.mehmetsolak.social.application.features.auth.commands.login;

import com.mehmetsolak.social.application.cqrs.Command;

public record LoginCommand(String handle, String password)
        implements Command<Void> { }
