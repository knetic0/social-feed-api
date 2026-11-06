package com.mehmetsolak.social.infrastructure.persistence.post;

import com.mehmetsolak.social.application.features.feed.ports.PostSummaryReader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostJpaRepository extends JpaRepository<PostJpaEntity, UUID> {
    @Query(value = """
        SELECT p.id AS postId, p.author_id AS authorId, u.handle AS authorHandle,
               p.body AS body, p.created_at AS createdAt,
               p.like_count AS likeCount, p.comment_count AS commentCount
        FROM posts p JOIN users u ON u.id = p.author_id
        WHERE p.id IN (:ids) AND p.deleted_at IS NULL
        """, nativeQuery = true)
    List<PostSummaryReader.PostSummary> findSummariesByIds(@Param("ids") List<UUID> ids);

    Optional<PostJpaEntity> findByIdAndDeletedAtIsNull(UUID id);
}
