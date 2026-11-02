CREATE TABLE comments
(
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL,
    post_id    UUID NOT NULL,
    body       TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_comments_user
        FOREIGN KEY (user_id)
            REFERENCES users (id),

    CONSTRAINT fk_comments_post
        FOREIGN KEY (post_id)
            REFERENCES posts (id),

    CONSTRAINT ck_comments_body_not_blank
        CHECK (length(btrim(body)) > 0)
);

CREATE INDEX idx_comments_post_created
    ON comments (post_id, created_at DESC, id DESC);

CREATE INDEX idx_comments_user_created
    ON comments (user_id, created_at DESC, id DESC);