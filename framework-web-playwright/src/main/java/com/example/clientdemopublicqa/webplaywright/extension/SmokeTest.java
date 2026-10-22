package com.example.clientdemopublicqa.webplaywright.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Composed annotation: {@code @Test} + {@code @Tag("smoke")}.
 * Use instead of {@code @Test} for tests that should be part of the smoke suite.
 *
 * <pre>
 * {@code @SmokeTest}
 * void homepageLoads() { ... }
 * </pre>
 *
 * Run smoke tests with: {@code mvn verify -Dgroups=smoke}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tag("smoke")
@Test
public @interface SmokeTest {
}
