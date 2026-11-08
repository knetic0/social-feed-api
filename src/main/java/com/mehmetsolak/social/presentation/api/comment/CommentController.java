package com.mehmetsolak.social.presentation.api.comment;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.comment.commands.create.CreateCommentCommand;
import com.mehmetsolak.social.application.features.comment.commands.create.CreateCommentCommandResponse;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.presentation.api.comment.dtos.request.CreateCommentRequest;
import com.mehmetsolak.social.presentation.security.Authenticated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@Tag(
        name = "Post",
        description = "Endpoints for post comments"
)
@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
@Authenticated
public class CommentController {

    private final CommandHandler<CreateCommentCommand, CreateCommentCommandResponse> createCommentCommandHandler;

    private final CurrentUser currentUser;

    public CommentController(
            CommandHandler<CreateCommentCommand, CreateCommentCommandResponse> createCommentCommandHandler,
            CurrentUser currentUser
    ) {
        this.createCommentCommandHandler = createCommentCommandHandler;
        this.currentUser = currentUser;
    }

    @Operation(
            summary = "Create a new comment on post"
    )
    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable UUID postId, @RequestBody CreateCommentRequest request
    ) {
        var userId = currentUser.getCurrentUserId();
        var command = new CreateCommentCommand(
                userId,
                postId,
                request.body()
        );
        var result = createCommentCommandHandler.execute(command);
        return ResponseEntity.created(
                URI.create("/api/v1/posts/" + postId + "/comments/" + result.id())).build();
    }
}
