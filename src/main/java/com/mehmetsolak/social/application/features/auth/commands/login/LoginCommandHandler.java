package com.mehmetsolak.social.application.features.auth.commands.login;

import com.mehmetsolak.social.application.cqrs.CommandHandler;

public class LoginCommandHandler implements CommandHandler<LoginCommand, Void> {

    @Override
    public Void execute(LoginCommand command) {
        return null;
    }
}
