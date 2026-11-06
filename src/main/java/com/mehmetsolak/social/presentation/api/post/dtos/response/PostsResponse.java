package com.mehmetsolak.social.presentation.api.post.dtos.response;

import java.util.List;

public record PostsResponse(List<PostDetailsResponse> items) {
}
