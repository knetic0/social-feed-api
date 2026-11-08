package com.mehmetsolak.social.presentation.api.reaction;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.reaction.commands.likePost.LikePostCommand;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
public class ReactionController {

    private final CommandHandler<LikePostCommand, Void> likePostCommandHandler;

    private final CurrentUser currentUser;

    public ReactionController(
            CommandHandler<LikePostCommand, Void> likePostCommandHandler,
            CurrentUser currentUser
    ) {
        this.likePostCommandHandler = likePostCommandHandler;
        this.currentUser = currentUser;
    }

    @PutMapping("/{postId}/likes")
    public ResponseEntity<Void> reaction(
            @PathVariable UUID postId
    ) {
        var command = new LikePostCommand(currentUser.getCurrentUserId(), postId);
        likePostCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }
}
