package com.mehmetsolak.social.presentation.api.auth.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Schema(example = "john.doe")
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @Pattern(
                regexp = "^[a-z0-9_]+$",
                message = "Handle can only contain lowercase letters, numbers and underscores"
        )
        String handle,

        @Schema(format = "password", example = "StrongPassword123!")
        @NotBlank(message = "Password is required")
        @Size(
                min = 8,
                max = 72,
                message = "Password must be between 8 and 72 characters"
        )
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
                message = "Password must contain uppercase, lowercase and numeric characters"
        )
        String password) {
}
