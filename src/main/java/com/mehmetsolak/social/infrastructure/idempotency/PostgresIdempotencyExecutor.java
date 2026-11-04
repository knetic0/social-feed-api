package com.mehmetsolak.social.infrastructure.idempotency;

import com.mehmetsolak.social.application.shared.exception.IdempotencyKeyReusedException;
import com.mehmetsolak.social.application.shared.exception.IdempotencyRequestInProgressException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class PostgresIdempotencyExecutor {

    private static final String SET_LOCK_TIMEOUT_SQL = "SET LOCAL lock_timeout = '10s'";

    private static final String LOCK_SQL =
            "SELECT 1 FROM pg_advisory_xact_lock(hashtextextended(?, 0))";

    private static final String FIND_SQL = """
            SELECT request_hash, response_body::text
            FROM idempotency_records
            WHERE user_id = ? AND operation = ? AND idem_key = ? AND expires_at > now()
            """;

    private static final String UPSERT_SQL = """
            INSERT INTO idempotency_records
                (user_id, operation, idem_key, request_hash, response_body, expires_at)
            VALUES (?, ?, ?, ?, ?::jsonb, ?)
            ON CONFLICT ON CONSTRAINT pk_idempotency_records DO UPDATE SET
                request_hash  = EXCLUDED.request_hash,
                response_body = EXCLUDED.response_body,
                created_at    = now(),
                expires_at    = EXCLUDED.expires_at
            WHERE idempotency_records.expires_at <= now()
            """;

    private final JdbcTemplate jdbc;
    private final TransactionTemplate tx;
    private final JsonMapper jsonMapper;
    private final Duration ttl;

    public PostgresIdempotencyExecutor(
            JdbcTemplate jdbc,
            TransactionTemplate transactionManager,
            JsonMapper jsonMapper,
            @Value("${app.idempotency.ttl:24h}") Duration ttl) {
        this.jdbc = jdbc;
        this.jsonMapper = jsonMapper;
        this.ttl = ttl;
        this.tx = transactionManager;
        this.tx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        this.tx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
    }

    public <R> R execute(UUID userId,
                         String operation,
                         UUID key,
                         String requestHash,
                         Class<R> responseType,
                         Supplier<R> action) {
        return tx.execute(status -> {
            acquireLock(userId, operation, key);

            List<StoredRecord> existing = jdbc.query(
                    FIND_SQL,
                    (rs, i) -> new StoredRecord(rs.getString(1), rs.getString(2)),
                    userId, operation, key);

            if (!existing.isEmpty()) {
                StoredRecord record = existing.getFirst();
                if (!record.requestHash().equals(requestHash))
                    throw new IdempotencyKeyReusedException(key);
                return deserialize(record.responseBody(), responseType);
            }

            R response = action.get();

            int affected = jdbc.update(
                    UPSERT_SQL,
                    userId, operation, key, requestHash,
                    serialize(response),
                    OffsetDateTime.now(ZoneOffset.UTC).plus(ttl));

            if (affected != 1)
                throw new IllegalStateException("Idempotency record could not be stored for key: " + key);

            return response;
        });
    }

    private void acquireLock(UUID userId, String operation, UUID key) {
        try {
            jdbc.execute(SET_LOCK_TIMEOUT_SQL);
            jdbc.queryForObject(LOCK_SQL, Integer.class, userId + ":" + operation + ":" + key);
        } catch (PessimisticLockingFailureException ex) {
            throw new IdempotencyRequestInProgressException(key, ex);
        }
    }

    private String serialize(Object response) {
        try {
            return jsonMapper.writeValueAsString(response);
        } catch (JacksonException ex) {
            throw new IllegalStateException("Idempotent response could not be serialized", ex);
        }
    }

    private <R> R deserialize(String json, Class<R> type) {
        try {
            return jsonMapper.readValue(json, type);
        } catch (JacksonException ex) {
            throw new IllegalStateException("Stored idempotent response could not be deserialized", ex);
        }
    }

    private record StoredRecord(String requestHash, String responseBody) { }
}
