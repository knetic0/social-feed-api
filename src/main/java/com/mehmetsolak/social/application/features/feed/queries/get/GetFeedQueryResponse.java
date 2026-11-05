package com.mehmetsolak.social.application.features.feed.queries.get;

import com.mehmetsolak.social.application.features.feed.ports.PostSummaryReader;

import java.util.List;

public record GetFeedQueryResponse(
        List<PostSummaryReader.PostSummary> items,
        Long nextCursor
) { }
