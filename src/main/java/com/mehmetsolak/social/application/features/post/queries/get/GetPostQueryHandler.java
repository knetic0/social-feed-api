package com.mehmetsolak.social.application.features.post.queries.get;

import com.mehmetsolak.social.application.cqrs.QueryHandler;
import com.mehmetsolak.social.application.features.post.exception.PostNotFoundException;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import com.mehmetsolak.social.domain.post.vobj.PostId;

public class GetPostQueryHandler implements QueryHandler<GetPostQuery, GetPostQueryResponse> {

    private final PostRepository postRepository;

    public GetPostQueryHandler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public GetPostQueryResponse execute(GetPostQuery query) {
        var postId = PostId.of(query.postId());

        var r = postRepository.findById(postId);
        if (r.isEmpty()) {
            throw new PostNotFoundException("Post not found.");
        }

        var post = r.get();

        return new GetPostQueryResponse(
                post.getId().value(),
                post.getAuthorId().value(),
                post.getBody().value(),
                post.getCreatedAt(),
                post.getLikeCount(),
                post.getCommentCount()
        );
    }
}
