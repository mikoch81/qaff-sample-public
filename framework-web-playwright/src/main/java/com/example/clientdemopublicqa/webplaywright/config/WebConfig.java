package com.example.clientdemopublicqa.webplaywright.config;

import java.util.Locale;
import java.util.Properties;

/**
 * Resolves web smoke target settings from
 * system property -> environment variable -> config.properties -> default.
 */
public record WebConfig(String target, String baseUrl) {

    public static final String TARGET_FIXTURE = "fixture";
    public static final String TARGET_EXTERNAL = "external";
    public static final String LOCAL_FIXTURE_SENTINEL = "local-fixture";

    private static final String SYSPROP_TARGET = "web.target";
    private static final String ENV_TARGET = "QAFF_WEB_TARGET";
    private static final String SYSPROP_BASE_URL = "web.baseUrl";
    private static final String ENV_BASE_URL = "QAFF_WEB_BASE_URL";

    public static WebConfig from(Properties props) {
        String resolvedTarget = firstNonBlank(
                System.getProperty(SYSPROP_TARGET),
                System.getenv(ENV_TARGET),
                props.getProperty("target"),
                TARGET_FIXTURE);

        String normalizedTarget = normalizeTarget(resolvedTarget);

        String resolvedBaseUrl = firstNonBlank(
                System.getProperty(SYSPROP_BASE_URL),
                System.getenv(ENV_BASE_URL),
                props.getProperty("baseUrl"),
                LOCAL_FIXTURE_SENTINEL);

        if (resolvedBaseUrl == null || resolvedBaseUrl.isBlank()) {
            resolvedBaseUrl = LOCAL_FIXTURE_SENTINEL;
        }

        return new WebConfig(normalizedTarget, resolvedBaseUrl.trim());
    }

    private static String normalizeTarget(String value) {
        if (value == null) {
            return TARGET_FIXTURE;
        }
        String normalized = value.trim().toLowerCase(Locale.ROOT);
        if (TARGET_EXTERNAL.equals(normalized)) {
            return TARGET_EXTERNAL;
        }
        return TARGET_FIXTURE;
    }

    private static String firstNonBlank(String first, String second, String third, String fallback) {
        if (first != null && !first.isBlank()) {
            return first;
        }
        if (second != null && !second.isBlank()) {
            return second;
        }
        if (third != null && !third.isBlank()) {
            return third;
        }
        return fallback;
    }
}
