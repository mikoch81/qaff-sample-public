package com.example.clientdemopublicqa.webplaywright.extension;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.opentest4j.TestAbortedException;

/**
 * JUnit 5 extension backing {@link RetryingTest}.
 *
 * <p>Each attempt is a real {@code @TestTemplate} invocation: a failed attempt
 * short of the limit is reported as aborted (skipped) and the next attempt
 * runs; the test passes on the first successful attempt and fails only when
 * every attempt has failed (the last failure propagates).
 *
 * <p>An exception-swallowing {@code TestExecutionExceptionHandler} alone
 * cannot re-execute a test body — it would report failing tests as passed.
 * Do not "simplify" this class back to that shape.
 */
public class RetryExtension implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod()
                .map(method -> method.isAnnotationPresent(RetryingTest.class))
                .orElse(false);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        RetryingTest retryingTest = context.getRequiredTestMethod()
                .getAnnotation(RetryingTest.class);
        int maxAttempts = Math.max(1, retryingTest.value());
        AttemptIterator attempts = new AttemptIterator(maxAttempts);
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(attempts, Spliterator.ORDERED), false);
    }

    /**
     * Lazily yields the next attempt only while the previous one failed and
     * attempts remain. JUnit consumes the stream one invocation at a time, so
     * {@link #hasNext()} runs after the previous attempt has finished.
     */
    private static final class AttemptIterator implements Iterator<TestTemplateInvocationContext> {
        private final int maxAttempts;
        private int attempt;
        private boolean lastAttemptFailed;

        private AttemptIterator(int maxAttempts) {
            this.maxAttempts = maxAttempts;
        }

        @Override
        public boolean hasNext() {
            return attempt == 0 || (lastAttemptFailed && attempt < maxAttempts);
        }

        @Override
        public TestTemplateInvocationContext next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            attempt++;
            lastAttemptFailed = false;
            return new AttemptContext(attempt);
        }

        private final class AttemptContext implements TestTemplateInvocationContext {
            private final int current;

            private AttemptContext(int current) {
                this.current = current;
            }

            @Override
            public String getDisplayName(int invocationIndex) {
                return "attempt " + current + " of " + maxAttempts;
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return List.of((TestExecutionExceptionHandler) (extensionContext, throwable) -> {
                    lastAttemptFailed = true;
                    if (current < maxAttempts) {
                        // abort (not pass) this attempt; hasNext() yields the next one
                        throw new TestAbortedException(
                                "attempt " + current + " of " + maxAttempts + " failed, retrying", throwable);
                    }
                    // all attempts exhausted — propagate the original failure
                    throw throwable;
                });
            }
        }
    }
}
