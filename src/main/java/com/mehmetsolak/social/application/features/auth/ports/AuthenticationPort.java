package com.mehmetsolak.social.application.features.auth.ports;

public interface AuthenticationPort {
    void authenticate(String handle, String password);
}
