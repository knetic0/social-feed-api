package com.mehmetsolak.social.application.features.feed.commands.distribute;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.feed.ports.FeedWriter;
import com.mehmetsolak.social.application.features.feed.ports.FollowerReader;

import java.util.List;
import java.util.UUID;

public class DistributePostCommandHandler implements CommandHandler<DistributePostCommand, Void> {

    private static final int BATCH_SIZE = 500;

    private final FeedWriter feedWriter;
    private final FollowerReader followerReader;

    public DistributePostCommandHandler(FeedWriter feedWriter, FollowerReader followerReader) {
        this.feedWriter = feedWriter;
        this.followerReader = followerReader;
    }

    @Override
    public Void execute(DistributePostCommand command) {
        UUID authorId = command.authorId();
        UUID cursor = null;

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
