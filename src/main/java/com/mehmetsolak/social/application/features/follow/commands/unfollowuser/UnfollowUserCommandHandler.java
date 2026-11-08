package com.mehmetsolak.social.application.features.follow.commands.unfollowuser;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.shared.exception.UserNotFoundException;
import com.mehmetsolak.social.domain.follow.repository.FollowRepository;
import com.mehmetsolak.social.domain.user.repository.UserRepository;
import com.mehmetsolak.social.domain.user.vobj.UserId;

public class UnfollowUserCommandHandler implements CommandHandler<UnfollowUserCommand, Void> {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public UnfollowUserCommandHandler(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    @Override
    public Void execute(UnfollowUserCommand command) {
        UserId followeeId = UserId.of(command.followeeId());
        if (!userRepository.existsById(followeeId)) {
            throw new UserNotFoundException("User not found");
        }

        UserId followerId = UserId.of(command.followerId());
        followRepository.deleteIfExists(followerId, followeeId);

        return null;
    }
}
