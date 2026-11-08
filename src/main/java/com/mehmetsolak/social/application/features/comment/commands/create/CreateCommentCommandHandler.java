package com.mehmetsolak.social.application.features.comment.commands.create;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.post.exception.PostNotFoundException;
import com.mehmetsolak.social.domain.comment.model.Comment;
import com.mehmetsolak.social.domain.comment.repository.CommentRepository;
import com.mehmetsolak.social.domain.comment.vobj.CommentBody;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import com.mehmetsolak.social.domain.post.vobj.PostId;
import com.mehmetsolak.social.domain.user.vobj.UserId;

public class CreateCommentCommandHandler implements CommandHandler<CreateCommentCommand, CreateCommentCommandResponse> {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CreateCommentCommandHandler(
            CommentRepository commentRepository,
            PostRepository postRepository
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CreateCommentCommandResponse execute(CreateCommentCommand command) {
        PostId postId = PostId.of(command.postId());
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        var userId = UserId.of(command.userId());
        var commentBody = CommentBody.of(command.body());
        var comment = Comment.of(userId, postId, commentBody);

        commentRepository.save(comment);

        post.incrementCommentCount();
        postRepository.save(post);

        return new CreateCommentCommandResponse(comment.getId());
    }
}
