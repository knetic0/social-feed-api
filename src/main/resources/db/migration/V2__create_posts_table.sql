CREATE TABLE posts
(
    id            UUID PRIMARY KEY,
    author_id     UUID NOT NULL,
    body          TEXT NOT NULL,
    created_at    TIMESTAMPTZ NOT NULL,
    deleted_at    TIMESTAMPTZ,
    like_count    BIGINT NOT NULL DEFAULT 0,
    comment_count BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT fk_posts_author
        FOREIGN KEY (author_id)
            REFERENCES users (id),

    CONSTRAINT ck_posts_like_count
        CHECK (like_count >= 0),

    CONSTRAINT ck_posts_comment_count
        CHECK (comment_count >= 0)
);

CREATE INDEX idx_posts_author_created
    ON posts (author_id, created_at DESC, id DESC)
    WHERE deleted_at IS NULL;

CREATE INDEX idx_posts_created
    ON posts (created_at DESC, id DESC)
    WHERE deleted_at IS NULL;