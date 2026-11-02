package com.mehmetsolak.social.infrastructure.security;

import com.mehmetsolak.social.application.features.auth.ports.PasswordHasher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordHasher implements PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordHasher(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String raw) {
        return passwordEncoder.encode(raw);
    }
}
