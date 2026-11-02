package com.mehmetsolak.social.application.features.auth.commands.login;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.auth.ports.AuthenticationPort;

public class LoginCommandHandler implements CommandHandler<LoginCommand, Void> {

    private final AuthenticationPort authenticationPort;

    public LoginCommandHandler(AuthenticationPort authenticationPort) {
        this.authenticationPort = authenticationPort;
    }

    @Override
    public Void execute(LoginCommand command) {
        authenticationPort.authenticate(
                command.handle(),
                command.password()
        );
        return null;
    }
}
