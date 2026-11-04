package com.mehmetsolak.social.presentation.api.follow;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.follow.commands.followuser.FollowUserCommand;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.domain.user.vobj.UserId;
import com.mehmetsolak.social.presentation.security.Authenticated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Authenticated
public class FollowController {

    private final CommandHandler<FollowUserCommand, Void> followUserCommandHandler;
    private final CurrentUser currentUser;

    public FollowController(
            CommandHandler<FollowUserCommand, Void> followUserCommandHandler,
            CurrentUser currentUser) {
        this.followUserCommandHandler = followUserCommandHandler;
        this.currentUser = currentUser;
    }

    @PostMapping("/{userId}/follow")
    public ResponseEntity<Void> follow(@PathVariable UUID userId) {
        var command = new FollowUserCommand(
                UserId.of(currentUser.getCurrentUserId()),
                UserId.of(userId));
        followUserCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }
}
