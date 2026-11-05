package com.mehmetsolak.social.application.features.feed.queries.get;

import com.mehmetsolak.social.application.cqrs.QueryHandler;
import com.mehmetsolak.social.application.features.feed.ports.FeedReader;
import com.mehmetsolak.social.application.features.feed.ports.PostSummaryReader;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GetFeedQueryHandler implements QueryHandler<GetFeedQuery, GetFeedQueryResponse> {

    private final FeedReader feedReader;
    private final PostSummaryReader postSummaryReader;

    public GetFeedQueryHandler(FeedReader feedReader, PostSummaryReader postSummaryReader) {
        this.feedReader = feedReader;
        this.postSummaryReader = postSummaryReader;
    }

    @Override
    public GetFeedQueryResponse execute(GetFeedQuery query) {
        var entries = feedReader.readFeed(
                query.userId(), query.cursor(), query.size());

        if (entries.isEmpty())
            return new GetFeedQueryResponse(List.of(), null);

        var ids = entries.stream()
                .map(FeedReader.FeedEntry::postId).toList();

        var byId = postSummaryReader.findByIds(ids).stream()
                .collect(Collectors.toMap(PostSummaryReader.PostSummary::postId, s -> s));

        var items = entries.stream()
                .map(e -> byId.get(e.postId()))
                .filter(Objects::nonNull)
                .toList();

        Long nextCursor = entries.size() < query.size()
                ? null
                : entries.getLast().createdAt().toEpochMilli();

        return new GetFeedQueryResponse(items, nextCursor);
    }
}
