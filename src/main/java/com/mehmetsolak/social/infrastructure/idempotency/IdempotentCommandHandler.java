package com.mehmetsolak.social.infrastructure.idempotency;

import com.mehmetsolak.social.application.cqrs.CommandHandler;
import com.mehmetsolak.social.application.cqrs.IdempotentCommand;
import com.mehmetsolak.social.application.shared.exception.InvalidIdempotencyKeyException;
import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.UUID;

public final class IdempotentCommandHandler<C extends IdempotentCommand<R>, R>
        implements CommandHandler<C, R> {

    private static final String KEY_FIELD = "idempotencyKey";

    private final CommandHandler<C, R> delegate;
    private final String operation;
    private final Class<R> responseType;
    private final CurrentUser currentUser;
    private final PostgresIdempotencyExecutor executor;
    private final JsonMapper jsonMapper;

    public IdempotentCommandHandler(
            CommandHandler<C, R> delegate,
            String operation,
            Class<R> responseType,
            CurrentUser currentUser,
            PostgresIdempotencyExecutor executor,
            JsonMapper jsonMapper) {
        if (operation == null || operation.isBlank() || operation.length() > 64)
            throw new IllegalArgumentException("operation must be 1..64 chars");
        this.delegate = delegate;
        this.operation = operation;
        this.responseType = responseType;
        this.currentUser = currentUser;
        this.executor = executor;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public R execute(C command) {
        UUID key = parseKey(command.idempotencyKey());
        UUID userId = UUID.fromString(String.valueOf(currentUser.getCurrentUserId()));
        String requestHash = hashRequest(command);

        return executor.execute(
                userId, operation, key, requestHash, responseType,
                () -> delegate.execute(command));
    }

    private static UUID parseKey(String raw) {
        if (raw == null || raw.isBlank())
            throw new InvalidIdempotencyKeyException("Idempotency-Key is required.");
        try {
            return UUID.fromString(raw.trim());
        } catch (IllegalArgumentException ex) {
            throw new InvalidIdempotencyKeyException("Idempotency-Key must be a valid UUID.");
        }
    }

    private String hashRequest(C command) {
        try {
            ObjectNode node = jsonMapper.valueToTree(command);
            node.remove(KEY_FIELD);
            byte[] canonical = jsonMapper.writeValueAsBytes(node);
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(canonical);
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Request hash could not be computed", ex);
        }
    }
}
