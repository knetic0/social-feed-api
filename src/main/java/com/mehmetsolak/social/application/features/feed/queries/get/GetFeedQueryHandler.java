package com.mehmetsolak.social.application.features.feed.queries.get;

import com.mehmetsolak.social.application.cqrs.QueryHandler;
import com.mehmetsolak.social.application.features.feed.ports.CelebrityReader;
import com.mehmetsolak.social.application.features.feed.ports.FeedReader;
import com.mehmetsolak.social.application.features.feed.ports.PostSummaryReader;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetFeedQueryHandler implements QueryHandler<GetFeedQuery, GetFeedQueryResponse> {

    private static final int MAX_CELEBS = 500;

    private final FeedReader feedReader;
    private final PostSummaryReader postSummaryReader;
    private final CelebrityReader celebrityReader;

    public GetFeedQueryHandler(
            FeedReader feedReader,
            PostSummaryReader postSummaryReader,
            CelebrityReader celebrityReader
    ) {
        this.feedReader = feedReader;
        this.postSummaryReader = postSummaryReader;
        this.celebrityReader = celebrityReader;
    }

    @Override
    public GetFeedQueryResponse execute(GetFeedQuery query) {
        Instant cursorInstant = query.cursor() != null
                ? Instant.ofEpochMilli(query.cursor())
                : null;

        var entries = feedReader.readFeed(
                query.userId(), query.cursor(), query.size());

        var pushIds = entries.stream()
                .map(FeedReader.FeedEntry::postId).toList();

        var pushSummaries = pushIds.isEmpty()
                ? List.<PostSummaryReader.PostSummary>of()
                : postSummaryReader.findByIds(pushIds);

        List<UUID> celebIds = celebrityReader.findCelebrityFolloweeIds(
                query.userId(), null, MAX_CELEBS);

        var pullSummaries = celebIds.isEmpty()
                ? List.<PostSummaryReader.PostSummary>of()
                : postSummaryReader.findRecentByAuthorIds(
                celebIds, cursorInstant, query.size());

        var merged = Stream.concat(pushSummaries.stream(), pullSummaries.stream())
                .sorted(Comparator.comparing(
                        PostSummaryReader.PostSummary::createdAt).reversed())
                .distinct()
                .limit(query.size())
                .toList();

        if (merged.isEmpty())
            return new GetFeedQueryResponse(List.of(), null);

        Long nextCursor = entries.size() < query.size()
                ? null
                : entries.getLast().createdAt().toEpochMilli();

        return new GetFeedQueryResponse(merged, nextCursor);
    }
}
