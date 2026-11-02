CREATE TABLE users
(
    id            UUID PRIMARY KEY,
    handle        VARCHAR(50) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    is_celebrity  BOOLEAN NOT NULL DEFAULT FALSE,
    created_at    TIMESTAMPTZ NOT NULL,

    CONSTRAINT uk_users_handle UNIQUE (handle)
);