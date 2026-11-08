package com.mehmetsolak.social.application.features.feed.commands.distribute;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.feed.ports.FeedWriter;
import com.mehmetsolak.social.application.features.feed.ports.FollowerReader;
import com.mehmetsolak.social.domain.user.model.User;
import com.mehmetsolak.social.domain.user.repository.UserRepository;
import com.mehmetsolak.social.domain.user.vobj.UserId;

import java.util.List;
import java.util.UUID;

public class DistributePostCommandHandler implements CommandHandler<DistributePostCommand, Void> {

    private static final int BATCH_SIZE = 500;

    private final FeedWriter feedWriter;
    private final FollowerReader followerReader;
    private final UserRepository userRepository;

    public DistributePostCommandHandler(
            FeedWriter feedWriter,
            FollowerReader followerReader,
            UserRepository userRepository
    ) {
        this.feedWriter = feedWriter;
        this.followerReader = followerReader;
        this.userRepository = userRepository;
    }

    @Override
    public Void execute(DistributePostCommand command) {
        UUID authorId = command.authorId();
        UUID cursor = null;

        User author = userRepository.findById(UserId.of(authorId))
                .orElse(null);

        if (author == null || author.isCelebrity()) return null;

        while (true) {
            List<UUID> followers = followerReader.findFollowerIds(
                    authorId,
                    cursor,
                    BATCH_SIZE
            );

            for (UUID followerId : followers) {
                feedWriter.addPost(
                        followerId,
                        command.postId(),
                        command.occurredAt()
                );
            }

            if (followers.size() < BATCH_SIZE) break;

            cursor = followers.getLast();
        }

        return null;
    }
}
