package com.learn.Resilience_UserService.controller;


import com.learn.Resilience_UserService.model.User;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private List<User> users = Arrays.asList(
            new User(1L, "John Doe", "john@example.com"),
            new User(2L, "Jane Smith", "jane@example.com"),
            new User(3L, "Bob Johnson", "bob@example.com")
    );

    @GetMapping
    @CircuitBreaker(name = "userEndpoint", fallbackMethod = "getAllUsersFallback")
    @RateLimiter(name = "userEndpointRateLimiter")
    @Bulkhead(name = "userEndpointBulkhead")
    public List<User> getAllUsers() {
        return users;
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "userEndpoint", fallbackMethod = "getUserByIdFallback")
    @Retry(name = "userEndpointRetry", fallbackMethod = "getUserByIdFallback")
    public User getUserById(@PathVariable Long id) {
        // Simulate occasional failure
        if (id == 2L && Math.random() > 0.5) {
            throw new RuntimeException("Database error");
        }

        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Fallback methods
    public List<User> getAllUsersFallback(Exception e) {
        return Arrays.asList(
                new User(0L, "Fallback User 1", "fallback@example.com"),
                new User(0L, "Fallback User 2", "fallback@example.com")
        );
    }

    public User getUserByIdFallback(Long id, Exception e) {
        return new User(id, "Fallback User", "fallback@example.com");
    }
}