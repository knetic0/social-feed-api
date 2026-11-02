package com.mehmetsolak.social.application.features.auth.commands.register;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.auth.ports.PasswordHasher;
import com.mehmetsolak.social.domain.user.model.User;
import com.mehmetsolak.social.domain.user.repository.UserRepository;
import com.mehmetsolak.social.domain.user.vobj.Handle;
import com.mehmetsolak.social.domain.user.vobj.PasswordHash;

public class RegisterCommandHandler implements CommandHandler<RegisterCommand, Void> {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public RegisterCommandHandler(
            UserRepository userRepository,
            PasswordHasher passwordHasher
    ) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Void execute(RegisterCommand command) {
        Handle handle = Handle.of(command.handle());

        if (userRepository.existsByHandle(handle)) {
            throw new UserAlreadyExistsException(
                    "User already exists"
            );
        }

        String hashed = passwordHasher.hash(command.password());
        PasswordHash passwordHash = PasswordHash.of(hashed);

        User user = User.of(handle, passwordHash, false);

        userRepository.save(user);

        return null;
    }
}
