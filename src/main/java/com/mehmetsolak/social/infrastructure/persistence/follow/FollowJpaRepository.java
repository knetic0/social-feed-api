package com.mehmetsolak.social.infrastructure.persistence.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface FollowJpaRepository extends JpaRepository<FollowJpaEntity, UUID> {

    @Query(value = """
            SELECT follower_id
            FROM follows
            WHERE followee_id = :followeeId
              AND (
                  CAST(:cursor AS uuid) IS NULL
                  OR follower_id > CAST(:cursor AS uuid)
              )
            ORDER BY follower_id
            LIMIT :limit
            """, nativeQuery = true)
    List<UUID> findFollowerIds(
            @Param("followeeId") UUID followeeId,
            @Param("cursor") UUID cursor,
            @Param("limit") int limit
    );

    @Modifying
    @Query(value = """
            INSERT INTO follows (id, follower_id, followee_id, created_at)
            VALUES (:id, :followerId, :followeeId, :createdAt)
            ON CONFLICT ON CONSTRAINT uk_follows_follower_followee DO NOTHING
            """, nativeQuery = true)
    int insertIfAbsent(@Param("id") UUID id,
                       @Param("followerId") UUID followerId,
                       @Param("followeeId") UUID followeeId,
                       @Param("createdAt") Instant createdAt);

    @Modifying
    @Query(value = """
        DELETE FROM follows
        WHERE follower_id = :followerId
          AND followee_id = :followeeId
        """, nativeQuery = true)
    int deleteIfExists(
            @Param("followerId") UUID followerId,
            @Param("followeeId") UUID followeeId
    );

    @Query(value = """
        SELECT f.followee_id
        FROM follows f
        JOIN users u ON u.id = f.followee_id
        WHERE f.follower_id = :followerId
          AND u.is_celebrity = true
          AND (
              CAST(:cursor AS uuid) IS NULL
              OR f.followee_id > CAST(:cursor AS uuid)
          )
        ORDER BY f.followee_id
        LIMIT :limit
        """, nativeQuery = true)
    List<UUID> findCelebrityFolloweeIds(
            @Param("followerId") UUID followerId,
            @Param("cursor") UUID cursor,
            @Param("limit") int limit
    );
}
