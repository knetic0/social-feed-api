package com.mehmetsolak.social.presentation.api.post;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommand;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommandResponse;
import com.mehmetsolak.social.presentation.api.post.dtos.request.CreatePostRequest;
import com.mehmetsolak.social.presentation.api.post.dtos.response.CreatePostResponse;
import com.mehmetsolak.social.presentation.security.Authenticated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(
        name = "Post",
        description = "Endpoints for post CRUD"
)
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final CommandHandler<CreatePostCommand, CreatePostCommandResponse> createPostCommandHandler;

    public PostController(CommandHandler<CreatePostCommand, CreatePostCommandResponse> createPostCommandHandler) {
        this.createPostCommandHandler = createPostCommandHandler;
    }

    @Operation(
            summary = "Create a new post",
            description = "Creates a new post"
    )
    @PostMapping("/")
    @Authenticated
    public ResponseEntity<CreatePostResponse> create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody CreatePostRequest request) {
        CreatePostCommand command = new CreatePostCommand(request.body(), idempotencyKey);
        var commandResponse = createPostCommandHandler.execute(command);
        var response = new CreatePostResponse(commandResponse.id());
        return ResponseEntity
                .created(URI.create("/api/v1/post/" + response.id()))
                .body(response);
    }
}
