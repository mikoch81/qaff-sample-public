package com.example.clientdemopublicqa.webplaywright.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Repeats a failed test up to {@link #value()} times before marking it failed.
 * Use sparingly — prefer fixing the root cause of flakiness.
 *
 * <p>Use <em>instead of</em> {@code @Test} (it is a {@code @TestTemplate};
 * combining it with {@code @Test} would run the method twice):
 *
 * <pre>
 * {@code @RetryingTest(3)}
 * void myFlakyTest() { ... }
 * </pre>
 *
 * <p>Failed attempts short of the limit are reported as aborted (skipped);
 * the test passes on the first successful attempt and fails only when every
 * attempt has failed.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@TestTemplate
@ExtendWith(RetryExtension.class)
public @interface RetryingTest {
    /** Maximum number of attempts (including the first). Must be >= 1. */
    int value() default 3;
}
