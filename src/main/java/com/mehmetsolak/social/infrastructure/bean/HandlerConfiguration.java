package com.mehmetsolak.social.infrastructure.bean;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.cqrs.QueryHandler;
import com.mehmetsolak.social.application.features.auth.commands.login.LoginCommand;
import com.mehmetsolak.social.application.features.auth.commands.login.LoginCommandHandler;
import com.mehmetsolak.social.application.features.auth.commands.register.RegisterCommand;
import com.mehmetsolak.social.application.features.auth.commands.register.RegisterCommandHandler;
import com.mehmetsolak.social.application.features.auth.ports.AuthenticationPort;
import com.mehmetsolak.social.application.features.auth.ports.PasswordHasher;
import com.mehmetsolak.social.application.features.feed.ports.FeedReader;
import com.mehmetsolak.social.application.features.feed.ports.FollowerReader;
import com.mehmetsolak.social.application.features.feed.ports.PostSummaryReader;
import com.mehmetsolak.social.application.features.feed.queries.get.GetFeedQuery;
import com.mehmetsolak.social.application.features.feed.queries.get.GetFeedQueryHandler;
import com.mehmetsolak.social.application.features.feed.queries.get.GetFeedQueryResponse;
import com.mehmetsolak.social.application.features.follow.commands.followuser.FollowUserCommand;
import com.mehmetsolak.social.application.features.follow.commands.followuser.FollowUserCommandHandler;
import com.mehmetsolak.social.application.features.follow.ports.UserLookupPort;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommand;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommandHandler;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommandResponse;
import com.mehmetsolak.social.application.features.feed.commands.distribute.DistributePostCommand;
import com.mehmetsolak.social.application.features.feed.commands.distribute.DistributePostCommandHandler;
import com.mehmetsolak.social.application.features.feed.ports.FeedWriter;
import com.mehmetsolak.social.application.features.post.queries.get.GetPostQuery;
import com.mehmetsolak.social.application.features.post.queries.get.GetPostQueryHandler;
import com.mehmetsolak.social.application.features.post.queries.get.GetPostQueryResponse;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.application.shared.ports.DomainEventPublisher;
import com.mehmetsolak.social.domain.follow.repository.FollowRepository;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import com.mehmetsolak.social.domain.user.repository.UserRepository;
import com.mehmetsolak.social.infrastructure.idempotency.IdempotentCommandHandler;
import com.mehmetsolak.social.infrastructure.idempotency.PostgresIdempotencyExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;
import tools.jackson.databind.json.JsonMapper;

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
            DomainEventPublisher domainEventPublisher,
            PostgresIdempotencyExecutor postgresIdempotencyExecutor,
            JsonMapper objectMapper
    ) {
        var handler = new CreatePostCommandHandler(
                postRepository,
                currentUser,
                domainEventPublisher
        );

        return new IdempotentCommandHandler<>(
                handler,
                "post.create",
                CreatePostCommandResponse.class,
                currentUser,
                postgresIdempotencyExecutor,
                objectMapper);
    }

    @Bean
    public CommandHandler<DistributePostCommand, Void> distributePostCommandHandler(
            FeedWriter feedWriter, FollowerReader followerReader
    ) {
        return new DistributePostCommandHandler(feedWriter, followerReader);
    }

    @Bean
    public CommandHandler<FollowUserCommand, Void> followUserCommandHandler(
            UserLookupPort userLookupPort,
            FollowRepository followRepository,
            DomainEventPublisher domainEventPublisher,
            TransactionTemplate transactionTemplate
    ) {
        var handler = new FollowUserCommandHandler(userLookupPort, followRepository, domainEventPublisher);
        return command -> transactionTemplate.execute(status -> handler.execute(command));
    }

    @Bean
    public QueryHandler<GetFeedQuery, GetFeedQueryResponse> getFeedQueryHandler(
            FeedReader feedReader, PostSummaryReader postSummaryReader
    ) {
        return new GetFeedQueryHandler(feedReader, postSummaryReader);
    }

    @Bean
    public QueryHandler<GetPostQuery, GetPostQueryResponse> getPostQueryHandler(
            PostRepository postRepository
    ) {
        return new GetPostQueryHandler(postRepository);
    }
}
