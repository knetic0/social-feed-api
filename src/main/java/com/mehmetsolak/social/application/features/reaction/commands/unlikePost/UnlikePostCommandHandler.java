package com.mehmetsolak.social.application.features.reaction.commands.unlikePost;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.post.exception.PostNotFoundException;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.reaction.repository.ReactionRepository;
import com.mehmetsolak.social.domain.user.vobj.UserId;

public class UnlikePostCommandHandler implements CommandHandler<UnlikePostCommand, Void> {

    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    public UnlikePostCommandHandler(PostRepository postRepository, ReactionRepository reactionRepository) {
        this.postRepository = postRepository;
        this.reactionRepository = reactionRepository;
    }

    @Override
    public Void execute(UnlikePostCommand command) {
        PostId postId = PostId.of(command.postId());

        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found!"));

        UserId userId = UserId.of(command.userId());

        post.decrementLikeCount();

        reactionRepository.deleteByUserIdAndPostId(userId, postId);
        postRepository.save(post);

        return null;
    }
}
