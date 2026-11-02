package com.mehmetsolak.social.presentation.api.auth.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(example = "john.doe")
        @NotBlank(message = "Username is required")
        String handle,

        @Schema(format = "password", example = "StrongPassword123!")
        @NotBlank(message = "Password is required")
        String password) {
}
