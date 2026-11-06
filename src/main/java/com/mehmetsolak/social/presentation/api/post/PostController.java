package com.mehmetsolak.social.presentation.api.post;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.cqrs.QueryHandler;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommand;
import com.mehmetsolak.social.application.features.post.commands.create.CreatePostCommandResponse;
import com.mehmetsolak.social.application.features.post.commands.delete.DeletePostCommand;
import com.mehmetsolak.social.application.features.post.queries.get.GetPostQuery;
import com.mehmetsolak.social.application.features.post.queries.get.GetPostQueryResponse;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.presentation.api.post.dtos.request.CreatePostRequest;
import com.mehmetsolak.social.presentation.api.post.dtos.response.CreatePostResponse;
import com.mehmetsolak.social.presentation.api.post.dtos.response.PostDetailsResponse;
import com.mehmetsolak.social.presentation.security.Authenticated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@Tag(
        name = "Post",
        description = "Endpoints for post CRUD"
)
@RestController
@RequestMapping("/api/v1/posts")
@Authenticated
public class PostController {

    private final CommandHandler<CreatePostCommand, CreatePostCommandResponse> createPostCommandHandler;
    private final QueryHandler<GetPostQuery, GetPostQueryResponse> getPostQueryHandler;
    private final CommandHandler<DeletePostCommand, Void> deletePostCommandHandler;

    private final CurrentUser currentUser;

    public PostController(
            CommandHandler<CreatePostCommand, CreatePostCommandResponse> createPostCommandHandler,
            QueryHandler<GetPostQuery, GetPostQueryResponse> getPostQueryHandler,
            CommandHandler<DeletePostCommand, Void> deletePostCommandHandler,
            CurrentUser currentUser) {
        this.createPostCommandHandler = createPostCommandHandler;
        this.getPostQueryHandler = getPostQueryHandler;
        this.deletePostCommandHandler = deletePostCommandHandler;
        this.currentUser = currentUser;
    }

    @Operation(
            summary = "Create a new post",
            description = "Creates a new post"
    )
    @PostMapping("/")
    public ResponseEntity<CreatePostResponse> create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody CreatePostRequest request) {
        CreatePostCommand command = new CreatePostCommand(request.body(), idempotencyKey);
        var commandResponse = createPostCommandHandler.execute(command);
        var response = new CreatePostResponse(commandResponse.id());
        return ResponseEntity
                .created(URI.create("/api/v1/posts/" + response.id()))
                .body(response);
    }

    @Operation(
            summary = "Get post by id",
            description = "Users can access their posts with id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailsResponse> get(@PathVariable UUID id) {
        var query = new GetPostQuery(id);
        var result = getPostQueryHandler.execute(query);
        var response = new PostDetailsResponse(
                result.id(),
                result.authorId(),
                result.body(),
                result.createdAt(),
                result.likeCount(),
                result.commentCount()
        );
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete post by id",
            description = "Users can delete their posts by id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        var command = new DeletePostCommand(currentUser.getCurrentUserId(), id);
        deletePostCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }
}
