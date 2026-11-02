CREATE TABLE reactions
(
    id         UUID PRIMARY KEY,
    user_id    UUID NOT NULL,
    post_id    UUID NOT NULL,
    type       VARCHAR(30) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT uk_reactions_user_post
        UNIQUE (user_id, post_id),

    CONSTRAINT fk_reactions_user
        FOREIGN KEY (user_id)
            REFERENCES users (id),

    CONSTRAINT fk_reactions_post
        FOREIGN KEY (post_id)
            REFERENCES posts (id)
);

CREATE INDEX idx_reactions_post_id
    ON reactions (post_id);