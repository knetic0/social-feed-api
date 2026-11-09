package com.mehmetsolak.social.presentation.security.ratelimit;

public class LoginAttemptExceededException extends RuntimeException {
    public LoginAttemptExceededException() {
        super("");
    }
}
