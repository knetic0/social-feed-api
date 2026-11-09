package com.mehmetsolak.social.presentation.security.ratelimit;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRateLimiter {
    int capacity() default 5;
    int refillMinutes() default 1;
}
