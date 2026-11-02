package com.mehmetsolak.social.infrastructure.bean;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.auth.commands.login.LoginCommand;
import com.mehmetsolak.social.application.features.auth.commands.login.LoginCommandHandler;
import com.mehmetsolak.social.application.features.auth.commands.register.RegisterCommand;
import com.mehmetsolak.social.application.features.auth.commands.register.RegisterCommandHandler;
import com.mehmetsolak.social.application.features.auth.ports.AuthenticationPort;
import com.mehmetsolak.social.application.features.auth.ports.PasswordHasher;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommand;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommandHandler;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommandResponse;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.application.shared.ports.DomainEventPublisher;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import com.mehmetsolak.social.domain.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class HandlerConfiguration {

    @Bean
    CommandHandler<RegisterCommand, Void> registerCommandHandler(
            UserRepository userRepository,
            PasswordHasher passwordHasher
    ) {
        return new RegisterCommandHandler(userRepository, passwordHasher);
    }

    @Bean
    CommandHandler<LoginCommand, Void> loginCommandHandler(
            AuthenticationPort authenticationPort) {
        return new LoginCommandHandler(authenticationPort);
    }

    @Bean
    public CommandHandler<CreatePostCommand, CreatePostCommandResponse> createPostCommandHandler(
            PostRepository postRepository,
            CurrentUser currentUser,
            DomainEventPublisher eventPublisher,
            TransactionTemplate transactionTemplate
    ) {
        var handler = new CreatePostCommandHandler(
                postRepository,
                currentUser,
                eventPublisher
        );

        return command -> transactionTemplate.execute(
                _ -> handler.execute(command)
        );
    }
}
