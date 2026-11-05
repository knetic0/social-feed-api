package com.mehmetsolak.social.presentation.api.feed;

import com.mehmetsolak.social.application.cqrs.QueryHandler;
import com.mehmetsolak.social.application.features.feed.queries.get.GetFeedQuery;
import com.mehmetsolak.social.application.features.feed.queries.get.GetFeedQueryResponse;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.presentation.api.feed.dtos.response.FeedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {

    private final QueryHandler<GetFeedQuery, GetFeedQueryResponse> getFeedQueryHandler;
    private final CurrentUser currentUser;

    public FeedController(CurrentUser currentUser, QueryHandler<GetFeedQuery, GetFeedQueryResponse> getFeedQueryHandler) {
        this.getFeedQueryHandler = getFeedQueryHandler;
        this.currentUser = currentUser;
    }

    @GetMapping
    public ResponseEntity<FeedResponse> feed(
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long cursor
    ) {
        var userId = currentUser.getCurrentUserId();
        var query = new GetFeedQuery(userId, size, cursor);
        var queryResult = getFeedQueryHandler.execute(query);
        var items = queryResult.items().stream()
                .map(item -> new FeedResponse.FeedItemResponse(
                        item.postId(),
                        item.authorId(),
                        item.authorHandle(),
                        item.body(),
                        item.createdAt(),
                        item.likeCount(),
                        item.commentCount()
                )).toList();
        return ResponseEntity.ok(new FeedResponse(items, queryResult.nextCursor()));
    }
}
