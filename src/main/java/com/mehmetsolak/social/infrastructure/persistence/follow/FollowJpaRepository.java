package com.mehmetsolak.social.infrastructure.persistence.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
