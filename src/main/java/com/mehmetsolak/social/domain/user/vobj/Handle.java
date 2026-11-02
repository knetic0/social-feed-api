package com.mehmetsolak.social.domain.user.vobj;

import com.mehmetsolak.social.domain.user.exception.InvalidHandleException;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.regex.Pattern;

public record Handle(String value) {

    private static final Pattern PATTERN =
            Pattern.compile("^[a-z0-9_]{4,15}$");

    public Handle {
        if (value == null) {
            throw new InvalidHandleException("Handle cannot be null");
        }

        value = value.trim()
                .replaceFirst("^@", "")
                .toLowerCase(Locale.ROOT);

        if(!PATTERN.matcher(value).matches()) {
            throw new InvalidHandleException(
                    "Handle must be contain 4-15 valid characters"
            );
        }
    }

    public static Handle of(String value) {
        return new Handle(value);
    }

    @Override
    public @NotNull String toString() {
        return "@" + value;
    }
}
