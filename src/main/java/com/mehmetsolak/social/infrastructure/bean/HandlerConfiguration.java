package com.mehmetsolak.social.infrastructure.bean;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.cqrs.QueryHandler;
import com.mehmetsolak.social.application.features.auth.commands.login.LoginCommand;
import com.mehmetsolak.social.application.features.auth.commands.login.LoginCommandHandler;
import com.mehmetsolak.social.application.features.auth.commands.register.RegisterCommand;
import com.mehmetsolak.social.application.features.auth.commands.register.RegisterCommandHandler;
import com.mehmetsolak.social.application.features.auth.ports.AuthenticationPort;
import com.mehmetsolak.social.application.features.auth.ports.PasswordHasher;
import com.mehmetsolak.social.application.features.comment.commands.create.CreateCommentCommand;
import com.mehmetsolak.social.application.features.comment.commands.create.CreateCommentCommandHandler;
import com.mehmetsolak.social.application.features.comment.commands.create.CreateCommentCommandResponse;
import com.mehmetsolak.social.application.features.feed.ports.*;
import com.mehmetsolak.social.application.features.feed.queries.get.GetFeedQuery;
import com.mehmetsolak.social.application.features.feed.queries.get.GetFeedQueryHandler;
import com.mehmetsolak.social.application.features.feed.queries.get.GetFeedQueryResponse;
import com.mehmetsolak.social.application.features.follow.commands.followuser.FollowUserCommand;
import com.mehmetsolak.social.application.features.follow.commands.followuser.FollowUserCommandHandler;
import com.mehmetsolak.social.application.features.follow.commands.unfollowuser.UnfollowUserCommand;
import com.mehmetsolak.social.application.features.follow.commands.unfollowuser.UnfollowUserCommandHandler;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommand;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommandHandler;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommandResponse;
import com.mehmetsolak.social.application.features.feed.commands.distribute.DistributePostCommand;
import com.mehmetsolak.social.application.features.feed.commands.distribute.DistributePostCommandHandler;
import com.mehmetsolak.social.application.features.post.commands.delete.DeletePostCommand;
import com.mehmetsolak.social.application.features.post.commands.delete.DeletePostCommandHandler;
import com.mehmetsolak.social.application.features.post.queries.get.GetPostQuery;
import com.mehmetsolak.social.application.features.post.queries.get.GetPostQueryHandler;
import com.mehmetsolak.social.application.features.post.queries.get.GetPostQueryResponse;
import com.mehmetsolak.social.application.features.post.queries.list.GetPostsQuery;
import com.mehmetsolak.social.application.features.post.queries.list.GetPostsQueryHandler;
import com.mehmetsolak.social.application.features.post.queries.list.GetPostsQueryResponse;
import com.mehmetsolak.social.application.features.reaction.commands.likePost.LikePostCommand;
import com.mehmetsolak.social.application.features.reaction.commands.likePost.LikePostCommandHandler;
import com.mehmetsolak.social.application.features.reaction.commands.unlikePost.UnlikePostCommand;
import com.mehmetsolak.social.application.features.reaction.commands.unlikePost.UnlikePostCommandHandler;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.application.shared.ports.DomainEventPublisher;
import com.mehmetsolak.social.domain.comment.repository.CommentRepository;
import com.mehmetsolak.social.domain.follow.repository.FollowRepository;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import com.mehmetsolak.social.domain.reaction.repository.ReactionRepository;
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
            FeedWriter feedWriter, FollowerReader followerReader, UserRepository userRepository
    ) {
        return new DistributePostCommandHandler(feedWriter, followerReader, userRepository);
    }

    @Bean
    public CommandHandler<FollowUserCommand, Void> followUserCommandHandler(
            UserRepository userRepository,
            FollowRepository followRepository,
            DomainEventPublisher domainEventPublisher,
            TransactionTemplate transactionTemplate
    ) {
        var handler = new FollowUserCommandHandler(userRepository, followRepository, domainEventPublisher);
        return command -> transactionTemplate.execute(
                _ -> handler.execute(command));
    }

    @Bean
    public CommandHandler<UnfollowUserCommand, Void> unfollowUserCommandHandler(
            UserRepository userRepository,
            FollowRepository followRepository,
            TransactionTemplate transactionTemplate
    ) {
        var handler = new UnfollowUserCommandHandler(userRepository, followRepository);

        return command -> transactionTemplate.execute(
                _ -> handler.execute(command)
        );
    }

    @Bean
    public QueryHandler<GetFeedQuery, GetFeedQueryResponse> getFeedQueryHandler(
            FeedReader feedReader, PostSummaryReader postSummaryReader, CelebrityReader celebrityReader
    ) {
        return new GetFeedQueryHandler(feedReader, postSummaryReader, celebrityReader);
    }

    @Bean
    public QueryHandler<GetPostQuery, GetPostQueryResponse> getPostQueryHandler(
            PostRepository postRepository
    ) {
        return new GetPostQueryHandler(postRepository);
    }

    @Bean
    public CommandHandler<DeletePostCommand, Void> deletePostCommandHandler(
            PostRepository postRepository,
            TransactionTemplate transactionTemplate
    ) {
        var handler = new DeletePostCommandHandler(postRepository);

        return command -> transactionTemplate.execute(
                _ -> handler.execute(command)
        );
    }

    @Bean
    public QueryHandler<GetPostsQuery, GetPostsQueryResponse> getPostsQueryHandler(
            PostRepository postRepository
    ) {
        return new GetPostsQueryHandler(postRepository);
    }

    @Bean
    public CommandHandler<LikePostCommand, Void> likePostCommandHandler(
            PostRepository postRepository, ReactionRepository reactionRepository,
            TransactionTemplate transactionTemplate
    ) {
        var handler = new LikePostCommandHandler(postRepository, reactionRepository);

        return command -> transactionTemplate.execute(
                _ -> handler.execute(command)
        );
    }

    @Bean
    public CommandHandler<UnlikePostCommand, Void> unlikePostCommandHandler(
            PostRepository postRepository, ReactionRepository reactionRepository,
            TransactionTemplate transactionTemplate
    ) {
        var handler = new UnlikePostCommandHandler(postRepository, reactionRepository);

        return command -> transactionTemplate.execute(
                _ -> handler.execute(command)
        );
    }

    @Bean
    public CommandHandler<CreateCommentCommand, CreateCommentCommandResponse> createCommentCommandHandler(
            PostRepository postRepository,
            CommentRepository commentRepository,
            CurrentUser currentUser,
            PostgresIdempotencyExecutor postgresIdempotencyExecutor,
            JsonMapper jsonMapper
    ) {
        var handler = new CreateCommentCommandHandler(commentRepository, postRepository);

        return new IdempotentCommandHandler<>(
                handler,
                "comment.create",
                CreateCommentCommandResponse.class,
                currentUser,
                postgresIdempotencyExecutor,
                jsonMapper);
    }
}
