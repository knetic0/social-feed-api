package com.mehmetsolak.social.application.features.auth.ports;

public interface PasswordHasher {
    String hash(String raw);
}
