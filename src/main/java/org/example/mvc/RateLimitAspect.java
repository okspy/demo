package org.example.mvc;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {

    private static final Map<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

    @Before("@annotation(rateLimit)")
    public void rateLimit(JoinPoint joinPoint, RateLimit rateLimit) throws Exception {
        String methodName = joinPoint.getSignature().toShortString();
        double permitsPerSecond = rateLimit.value();

        RateLimiter rateLimiter = rateLimiters.computeIfAbsent(methodName, k -> RateLimiter.create(permitsPerSecond));
        if (!rateLimiter.tryAcquire()) {
            throw new Exception("Too many requests for method: " + methodName);
        }
    }
}
