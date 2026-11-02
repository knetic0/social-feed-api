package com.mehmetsolak.social.infrastructure.security;

import com.mehmetsolak.social.application.shared.ports.CurrentUser;
import com.mehmetsolak.social.domain.user.model.User;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SpringCurrentUserProvider implements CurrentUser {

    @Override
    public UUID getCurrentUserId() {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof SecurityUserDetails(User user
        ))) {
            throw new AuthenticationCredentialsNotFoundException(
                    "User is not authenticated"
            );
        }

        return user.getId().value();
    }
}
