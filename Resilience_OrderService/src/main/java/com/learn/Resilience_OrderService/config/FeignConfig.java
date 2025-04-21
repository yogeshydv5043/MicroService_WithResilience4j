package com.learn.Resilience_OrderService.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    private static final Logger logger = LoggerFactory.getLogger(FeignConfig.class);

    @Bean
    public RegistryEventConsumer<CircuitBreaker> circuitBreakerEventConsumer() {
        return new RegistryEventConsumer<>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher()
                        .onStateTransition(event -> logger.info("CircuitBreaker {} state changed from {} to {}",
                                event.getCircuitBreakerName(), event.getStateTransition().getFromState(),
                                event.getStateTransition().getToState()));
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
            }
        };
    }

    @Bean
    public RegistryEventConsumer<Retry> retryEventConsumer() {
        return new RegistryEventConsumer<>() {
            @Override
            public void onEntryAddedEvent(EntryAddedEvent<Retry> entryAddedEvent) {
                entryAddedEvent.getAddedEntry().getEventPublisher()
                        .onRetry(event -> logger.info("Retry attempt {} for {}", event.getNumberOfRetryAttempts(),
                                event.getName()));
            }

            @Override
            public void onEntryRemovedEvent(EntryRemovedEvent<Retry> entryRemoveEvent) {
            }

            @Override
            public void onEntryReplacedEvent(EntryReplacedEvent<Retry> entryReplacedEvent) {
            }
        };
    }
}