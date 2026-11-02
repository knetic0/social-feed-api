package com.mehmetsolak.social.infrastructure.security;

import com.mehmetsolak.social.domain.user.model.User;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record SecurityUserDetails(User user) implements UserDetails {
    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPasswordHash().value();
    }

    @Override
    @NullMarked
    public String getUsername() {
        return user.getHandle().value();
    }
}
