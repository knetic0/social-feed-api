package com.mehmetsolak.social.application.features.post.queries.list;

import com.mehmetsolak.social.application.cqrs.QueryHandler;
import com.mehmetsolak.social.domain.post.repository.PostRepository;
import com.mehmetsolak.social.domain.user.vobj.UserId;

public class GetPostsQueryHandler implements QueryHandler<GetPostsQuery, GetPostsQueryResponse> {

    private final PostRepository postRepository;

    public GetPostsQueryHandler(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public GetPostsQueryResponse execute(GetPostsQuery query) {
        var posts = postRepository.findByAuthorId(
                UserId.of(query.authorId())
        );
        var items = posts.stream().map(
                post -> new PostItem(
                        post.getId().value(),
                        post.getAuthorId().value(),
                        post.getBody().value(),
                        post.getCreatedAt(),
                        post.getLikeCount(),
                        post.getCommentCount()
                )
        ).toList();
        return new GetPostsQueryResponse(items);
    }
}
