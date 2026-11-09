package com.mehmetsolak.social.presentation.security.ratelimit;

import com.mehmetsolak.social.presentation.api.auth.dtos.request.LoginRequest;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitingAspect {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Around("@annotation(loginRateLimiter)")
    public Object checkLoginAttempt(
            ProceedingJoinPoint joinPoint,
            LoginRateLimiter loginRateLimiter
    ) throws Throwable {
        String key = resolveLoginAttemptKey(joinPoint);

        Bucket bucket = cache.computeIfAbsent(key, k -> createBucket(loginRateLimiter));

        if (!bucket.tryConsume(1)) {
            throw new LoginAttemptExceededException();
        }

        return joinPoint.proceed();
    }

    private Bucket createBucket(LoginRateLimiter loginRateLimiter) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(loginRateLimiter.capacity())
                .refillIntervally(loginRateLimiter.capacity(), Duration.ofMinutes(loginRateLimiter.refillMinutes()))
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private String resolveLoginAttemptKey(ProceedingJoinPoint joinPoint) {
        for(Object arg: joinPoint.getArgs()) {
            if(arg instanceof LoginRequest request) {
                return "login:" + request.handle();
            }
        }
        throw new IllegalStateException("LoginRateLimiter requires a LoginRequest params");
    }
}
