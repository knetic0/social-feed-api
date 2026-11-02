package com.mehmetsolak.social.infrastructure.handler;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.auth.commands.register.RegisterCommand;
import com.mehmetsolak.social.application.features.auth.commands.register.RegisterCommandHandler;
import com.mehmetsolak.social.application.features.auth.ports.PasswordHasher;
import com.mehmetsolak.social.domain.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlerConfiguration {

    @Bean
    CommandHandler<RegisterCommand, Void> registerCommandHandler(
            UserRepository userRepository,
            PasswordHasher passwordHasher
    ) {
        return new RegisterCommandHandler(userRepository, passwordHasher);
    }
}
