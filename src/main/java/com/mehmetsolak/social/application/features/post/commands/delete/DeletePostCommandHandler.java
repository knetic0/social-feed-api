package com.mehmetsolak.social.application.features.post.commands.delete;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.post.exception.PostNotFoundException;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.user.vobj.UserId;

public class DeletePostCommandHandler implements CommandHandler<DeletePostCommand, Void> {

    private final PostRepository postRepository;

    public DeletePostCommandHandler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Void execute(DeletePostCommand command) {
        var postId = PostId.of(command.postId());

        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found!"));

        var userId = UserId.of(command.userId());

        post.delete(userId);
        postRepository.save(post);

        return null;
    }
}
