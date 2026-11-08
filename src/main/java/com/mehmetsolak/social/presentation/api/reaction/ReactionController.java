package com.mehmetsolak.social.presentation.api.reaction;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.reaction.commands.likePost.LikePostCommand;
import com.mehmetsolak.social.application.features.reaction.commands.unlikePost.UnlikePostCommand;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.presentation.security.Authenticated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
        name = "Post",
        description = "Endpoints for post reactions"
)
@RestController
@RequestMapping("/api/v1/posts")
@Authenticated
public class ReactionController {

    private final CommandHandler<LikePostCommand, Void> likePostCommandHandler;
    private final CommandHandler<UnlikePostCommand, Void> unlikePostCommandHandler;

    private final CurrentUser currentUser;

    public ReactionController(
            CommandHandler<LikePostCommand, Void> likePostCommandHandler,
            CommandHandler<UnlikePostCommand, Void> unlikePostCommandHandler,
            CurrentUser currentUser
    ) {
        this.likePostCommandHandler = likePostCommandHandler;
        this.unlikePostCommandHandler = unlikePostCommandHandler;
        this.currentUser = currentUser;
    }

    @Operation(
            summary = "Like post"
    )
    @PutMapping("/{postId}/likes")
    public ResponseEntity<Void> like(
            @PathVariable UUID postId
    ) {
        var command = new LikePostCommand(currentUser.getCurrentUserId(), postId);
        likePostCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Unlike post"
    )
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<Void> unlike(@PathVariable UUID postId) {
        var command = new UnlikePostCommand(currentUser.getCurrentUserId(), postId);
        unlikePostCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }
}
