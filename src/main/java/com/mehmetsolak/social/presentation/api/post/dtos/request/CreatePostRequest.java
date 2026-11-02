package com.mehmetsolak.social.presentation.api.post.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
        @NotBlank(message = "Post body is required")
        @Size(
                max = 280,
                message = "Post body cannot exceed 280 characters"
        )
        String body
) {
}
