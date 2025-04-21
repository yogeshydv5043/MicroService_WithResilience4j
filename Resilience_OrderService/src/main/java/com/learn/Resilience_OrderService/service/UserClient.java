package com.learn.Resilience_OrderService.service;


import com.learn.Resilience_OrderService.config.FeignConfig;
import com.learn.Resilience_OrderService.dto.User;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/api/users/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    @Retry(name = "userServiceRetry", fallbackMethod = "getUserFallback")
    @RateLimiter(name = "userServiceRateLimiter")
    @Bulkhead(name = "userServiceBulkhead")
    User getUserById(@PathVariable Long id);

    default User getUserFallback(Long id, Exception e) {
        return new User(id, "Fallback User", "fallback@example.com");
    }
}