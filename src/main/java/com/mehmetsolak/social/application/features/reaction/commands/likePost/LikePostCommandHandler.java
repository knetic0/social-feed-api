package com.mehmetsolak.social.application.features.reaction.commands.likePost;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.post.exception.PostNotFoundException;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.reaction.model.Reaction;
import com.mehmetsolak.social.domain.reaction.repository.ReactionRepository;
import com.mehmetsolak.social.domain.user.vobj.UserId;

public class LikePostCommandHandler implements CommandHandler<LikePostCommand, Void> {

    private final PostRepository postRepository;
    private final ReactionRepository reactionRepository;

    public LikePostCommandHandler(PostRepository postRepository, ReactionRepository reactionRepository) {
        this.postRepository = postRepository;
        this.reactionRepository = reactionRepository;
    }

    @Override
    public Void execute(LikePostCommand command) {
        PostId postId = PostId.of(command.postId());

        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        UserId userId = UserId.of(command.userId());

        Reaction reaction = Reaction.of(userId, postId);
        reactionRepository.save(reaction);

        post.incrementLikeCount();
        postRepository.save(post);

        return null;
    }
}
