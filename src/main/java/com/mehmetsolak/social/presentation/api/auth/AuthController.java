package com.mehmetsolak.social.presentation.api.auth;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.auth.commands.register.RegisterCommand;
import com.mehmetsolak.social.presentation.api.auth.dtos.request.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Authentication",
        description = "Endpoints for registration and authentication"
)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final CommandHandler<RegisterCommand, Void> registerCommandHandler;

    public AuthController(
            CommandHandler<RegisterCommand, Void> registerCommandHandler
    ) {
        this.registerCommandHandler = registerCommandHandler;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with a unique handle",
            security = {}
    )
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody RegisterRequest request) {
        RegisterCommand command = new RegisterCommand(
                request.handle(),
                request.password()
        );
        registerCommandHandler.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
