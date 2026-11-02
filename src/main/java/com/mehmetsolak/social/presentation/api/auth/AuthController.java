package com.mehmetsolak.social.presentation.api.auth;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.features.auth.commands.login.LoginCommand;
import com.mehmetsolak.social.application.features.auth.commands.register.RegisterCommand;
import com.mehmetsolak.social.presentation.api.auth.dtos.request.LoginRequest;
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
    private final CommandHandler<LoginCommand, Void> loginCommandHandler;

    public AuthController(
            CommandHandler<RegisterCommand, Void> registerCommandHandler,
            CommandHandler<LoginCommand, Void> loginCommandHandler
    ) {
        this.registerCommandHandler = registerCommandHandler;
        this.loginCommandHandler = loginCommandHandler;
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

    @Operation(
            summary = "Login with account",
            description = "Login with existing account",
            security = {}
    )
    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest request) {
        LoginCommand command = new LoginCommand(
                request.handle(), request.password()
        );
        loginCommandHandler.execute(command);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
