package com.mehmetsolak.social.infrastructure.persistence.post;

import com.mehmetsolak.social.application.features.feed.ports.PostSummaryReader;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class JpaPostSummaryReaderImpl implements PostSummaryReader {

    private final PostJpaRepository repository;

    public JpaPostSummaryReaderImpl(PostJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PostSummary> findByIds(List<UUID> postIds) {
        return repository.findSummariesByIds(postIds);
    }

    @Override
    public List<PostSummary> findRecentByAuthorIds(List<UUID> authorIds, Instant cursor, int limit) {
        return repository.findRecentByAuthorIds(authorIds, cursor, limit);
    }
}
