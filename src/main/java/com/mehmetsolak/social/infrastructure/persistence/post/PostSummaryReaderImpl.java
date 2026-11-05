package com.mehmetsolak.social.infrastructure.persistence.post;

import com.mehmetsolak.social.application.features.feed.ports.PostSummaryReader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PostSummaryReaderImpl implements PostSummaryReader {

    private final PostJpaRepository repository;

    public PostSummaryReaderImpl(PostJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PostSummary> findByIds(List<UUID> postIds) {
        return repository.findSummariesByIds(postIds);
    }
}
