package com.mehmetsolak.social.presentation.api.post.dtos.response;

import java.time.Instant;
import java.util.UUID;

public record PostDetailsResponse(
        UUID id,
        UUID authorId,
        String body,
        Instant createdAt,
        long likeCount,
        long commentCount
) {
}
