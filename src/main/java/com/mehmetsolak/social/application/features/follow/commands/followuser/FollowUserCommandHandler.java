package com.mehmetsolak.social.application.features.follow.commands.followuser;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.follow.ports.UserLookupPort;
import com.mehmetsolak.social.application.shared.exception.UserNotFoundException;
import com.mehmetsolak.social.application.shared.ports.DomainEventPublisher;
import com.mehmetsolak.social.domain.follow.model.Follow;
import com.mehmetsolak.social.domain.follow.repository.FollowRepository;

import java.util.List;

public class FollowUserCommandHandler implements CommandHandler<FollowUserCommand, Void> {

    private final UserLookupPort userLookupPort;
    private final FollowRepository followRepository;
    private final DomainEventPublisher domainEventPublisher;

    public FollowUserCommandHandler(
            UserLookupPort userLookupPort,
            FollowRepository followRepository,
            DomainEventPublisher domainEventPublisher) {
        this.userLookupPort = userLookupPort;
        this.followRepository = followRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public Void execute(FollowUserCommand command) {
        if (!userLookupPort.exists(command.followeeId())) {
            throw new UserNotFoundException("User not found");
        }

        var follow = Follow.of(command.followerId(), command.followeeId());
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
