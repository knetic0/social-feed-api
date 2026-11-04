CREATE TABLE idempotency_records (
     user_id       UUID         NOT NULL,
     operation     VARCHAR(64)  NOT NULL,
     idem_key      UUID         NOT NULL,
     request_hash  CHAR(64)     NOT NULL,
     response_body JSONB        NOT NULL,
     created_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
     expires_at    TIMESTAMPTZ  NOT NULL,
     CONSTRAINT pk_idempotency_records PRIMARY KEY (user_id, operation, idem_key)
);

CREATE INDEX ix_idempotency_records_expires_at ON idempotency_records (expires_at);