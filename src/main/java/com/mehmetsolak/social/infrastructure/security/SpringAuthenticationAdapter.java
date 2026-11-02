package com.mehmetsolak.social.infrastructure.security;

import com.mehmetsolak.social.application.features.auth.ports.AuthenticationPort;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SpringAuthenticationAdapter implements AuthenticationPort {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public SpringAuthenticationAdapter(
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.request = request;
        this.response = response;
    }

    @Override
    public void authenticate(String handle, String password) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        handle,
                        password
                )
        );

        request.getSession(true);
        request.changeSessionId();

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(
                context,
                request,
                response
        );
    }
}
