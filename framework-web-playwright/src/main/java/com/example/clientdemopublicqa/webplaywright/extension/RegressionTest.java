package com.example.clientdemopublicqa.webplaywright.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Composed annotation: {@code @Test} + {@code @Tag("regression")}.
 * Use instead of {@code @Test} for regression suite tests.
 *
 * <pre>
 * {@code @RegressionTest}
 * void checkoutFlow() { ... }
 * </pre>
 *
 * Run regression tests with: {@code mvn verify -Dgroups=regression}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tag("regression")
@Test
public @interface RegressionTest {
}
