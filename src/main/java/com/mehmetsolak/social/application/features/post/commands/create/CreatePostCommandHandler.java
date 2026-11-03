package com.mehmetsolak.social.application.features.post.commands.create;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.application.shared.ports.DomainEventPublisher;
import com.mehmetsolak.social.domain.post.model.Post;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import com.mehmetsolak.social.domain.post.vobj.PostBody;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.util.List;

public class CreatePostCommandHandler
        implements CommandHandler<CreatePostCommand, CreatePostCommandResponse> {

    private final PostRepository postRepository;
    private final CurrentUser currentUser;
    private final DomainEventPublisher domainEventPublisher;

    public CreatePostCommandHandler(
            PostRepository postRepository,
            CurrentUser currentUser,
            DomainEventPublisher domainEventPublisher) {
        this.postRepository = postRepository;
        this.currentUser = currentUser;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public CreatePostCommandResponse execute(CreatePostCommand command) {
        UserId authorId = UserId.of(currentUser.getCurrentUserId());
        PostBody body = PostBody.of(command.body());
        Post post = Post.of(authorId, body);

        postRepository.save(post);

        var events = List.copyOf(post.getDomainEvents());
        for (var event : events)
            domainEventPublisher.publish(event);
        post.clearDomainEvents();

        return new CreatePostCommandResponse(post.getId().value());
    }
}
