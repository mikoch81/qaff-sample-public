package com.example.clientdemopublicqa.core.util;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * Dependency-free retry helper for flaky operations such as polling for
 * eventual consistency in API / integration tests.
 *
 * <p>This is distinct from the JUnit 5 {@code @RetryingTest} annotation, which
 * retries an entire test method. {@code Retry} retries a single block or
 * supplier inline, re-throwing the last failure (a {@link RuntimeException} or
 * {@link AssertionError}) if every attempt fails.
 *
 * <pre>{@code
 * String status = Retry.get(() -> client.fetchStatus(), 5, Duration.ofMillis(200));
 * Retry.run(() -> assertTrue(resource.isReady()), 3, Duration.ofSeconds(1));
 * }</pre>
 */
public final class Retry {

    private Retry() {
    }

    /**
     * Invoke {@code action} up to {@code attempts} times, sleeping {@code delay}
     * between failed attempts. Re-throws the last failure if all attempts fail.
     */
    public static void run(Runnable action, int attempts, Duration delay) {
        get(() -> {
            action.run();
            return null;
        }, attempts, delay);
    }

    /**
     * Invoke {@code supplier} up to {@code attempts} times, returning the first
     * successful result. Sleeps {@code delay} between failed attempts and
     * re-throws the last failure if all attempts fail.
     */
    public static <T> T get(Supplier<T> supplier, int attempts, Duration delay) {
        if (attempts < 1) {
            throw new IllegalArgumentException("attempts must be >= 1, got " + attempts);
        }
        Throwable last = null;
        for (int attempt = 1; attempt <= attempts; attempt++) {
            try {
                return supplier.get();
            } catch (RuntimeException | AssertionError e) {
                last = e;
                if (attempt < attempts) {
                    sleep(delay);
                }
            }
        }
        if (last instanceof RuntimeException) {
            throw (RuntimeException) last;
        }
        throw (AssertionError) last;
    }

    private static void sleep(Duration delay) {
        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Retry interrupted while waiting between attempts", e);
        }
    }
}
