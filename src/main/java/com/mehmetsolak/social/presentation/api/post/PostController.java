package com.mehmetsolak.social.presentation.api.post;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommand;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommandResponse;
import com.mehmetsolak.social.presentation.api.post.dtos.request.CreatePostRequest;
import com.mehmetsolak.social.presentation.api.post.dtos.response.CreatePostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Post",
        description = "Endpoints for post CRUD"
)
@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    private final CommandHandler<CreatePostCommand, CreatePostCommandResponse> createPostCommandHandler;

    public PostController(CommandHandler<CreatePostCommand, CreatePostCommandResponse> createPostCommandHandler) {
        this.createPostCommandHandler = createPostCommandHandler;
    }

    @Operation(
            summary = "Create a new post",
            description = "Creates a new post",
            security = {}
    )
    @PostMapping("/")
    public ResponseEntity<CreatePostResponse> create(
            @Valid @RequestBody CreatePostRequest request) {
        CreatePostCommand command = new CreatePostCommand(request.body());
        var commandResponse = createPostCommandHandler.execute(command);
        var response = new CreatePostResponse();
        return ResponseEntity.ok(response);
    }
}
