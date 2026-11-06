package com.mehmetsolak.social.application.features.post.queries.list;

import java.time.Instant;
import java.util.UUID;

public record PostItem(UUID id,
                       UUID authorId,
                       String body,
                       Instant createdAt,
                       long likeCount,
                       long commentCount) { }
