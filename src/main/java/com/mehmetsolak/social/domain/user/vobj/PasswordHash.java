package com.mehmetsolak.social.domain.user.vobj;

import com.mehmetsolak.social.domain.user.exception.InvalidPasswordException;
import org.jetbrains.annotations.NotNull;

public record PasswordHash(String value) {

    public PasswordHash {
        if (value == null) {
            throw new InvalidPasswordException("Password cannot be null");
        }
    }

    public static PasswordHash of(String value) {
        return new PasswordHash(value);
    }

    @Override
    public @NotNull String toString() {
        return "[********]";
    }
}
