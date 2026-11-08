package com.mehmetsolak.social.presentation.api.follow;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.follow.commands.followuser.FollowUserCommand;
import com.mehmetsolak.social.application.features.follow.commands.unfollowuser.UnfollowUserCommand;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.domain.user.vobj.UserId;
import com.mehmetsolak.social.presentation.security.Authenticated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
        name = "User",
        description = "Endpoints for user follows"
)
@RestController
@RequestMapping("/api/v1/users")
@Authenticated
public class FollowController {

    private final CommandHandler<FollowUserCommand, Void> followUserCommandHandler;
    private final CommandHandler<UnfollowUserCommand, Void> unfollowUserCommandHandler;

    private final CurrentUser currentUser;

    public FollowController(
            CommandHandler<FollowUserCommand, Void> followUserCommandHandler,
            CommandHandler<UnfollowUserCommand, Void> unfollowUserCommandHandler,
            CurrentUser currentUser) {
        this.followUserCommandHandler = followUserCommandHandler;
        this.unfollowUserCommandHandler = unfollowUserCommandHandler;
        this.currentUser = currentUser;
    }

    @Operation(
            summary = "Follow user"
    )
    @PutMapping("/{userId}/follow")
    public ResponseEntity<Void> follow(@PathVariable UUID userId) {
        var command = new FollowUserCommand(currentUser.getCurrentUserId(), userId);
        followUserCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Unfollow user"
    )
    @DeleteMapping("/{userId}/follow")
    public ResponseEntity<Void> unfollow(@PathVariable UUID userId) {
        var command = new UnfollowUserCommand(currentUser.getCurrentUserId(), userId);
        unfollowUserCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }
}
