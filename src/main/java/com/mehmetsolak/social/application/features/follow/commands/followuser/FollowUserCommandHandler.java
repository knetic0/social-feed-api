package com.mehmetsolak.social.application.features.follow.commands.followuser;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.shared.exception.UserNotFoundException;
import com.mehmetsolak.social.application.shared.ports.DomainEventPublisher;
import com.mehmetsolak.social.domain.follow.model.Follow;
import com.mehmetsolak.social.domain.follow.repository.FollowRepository;
import com.mehmetsolak.social.domain.user.repository.UserRepository;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.util.List;

public class FollowUserCommandHandler implements CommandHandler<FollowUserCommand, Void> {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final DomainEventPublisher domainEventPublisher;

    public FollowUserCommandHandler(
            UserRepository userRepository,
            FollowRepository followRepository,
            DomainEventPublisher domainEventPublisher) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public Void execute(FollowUserCommand command) {
        UserId followeeId = UserId.of(command.followeeId());
        if (!userRepository.existsById(followeeId)) {
            throw new UserNotFoundException("User not found");
        }

        var followerId = UserId.of(command.followerId());
        var follow = Follow.of(followerId, followeeId);
        if (followRepository.insertIfAbsent(follow)) {
            var events = List.copyOf(follow.getDomainEvents());
            for (var event : events) {
                domainEventPublisher.publish(event);
            }
            follow.clearDomainEvents();
        }

        return null;
    }
}
