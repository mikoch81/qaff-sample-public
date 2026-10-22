package com.example.clientdemopublicqa.webplaywright.config;

import java.util.Properties;

/**
 * What the generated home-page smoke expects to find: a heading (CSS selector
 * + exact text) and at least one repeated item (CSS selector). Resolved from
 * system property -> environment variable -> config.properties -> default,
 * where the defaults describe the bundled fixture.
 */
public record SmokeExpectations(String headingSelector, String headingText, String itemSelector) {

    public static final String DEFAULT_HEADING_SELECTOR = "[data-testid='store-heading']";
    public static final String DEFAULT_HEADING_TEXT = "Demo eCommerce Store";
    public static final String DEFAULT_ITEM_SELECTOR = "[data-testid='product-card']";

    public static SmokeExpectations defaults() {
        return new SmokeExpectations(DEFAULT_HEADING_SELECTOR, DEFAULT_HEADING_TEXT, DEFAULT_ITEM_SELECTOR);
    }

    public static SmokeExpectations from(Properties props) {
        return new SmokeExpectations(
                resolve("web.smoke.headingSelector", "QAFF_WEB_SMOKE_HEADING_SELECTOR",
                        props.getProperty("smoke.headingSelector"), DEFAULT_HEADING_SELECTOR),
                resolve("web.smoke.headingText", "QAFF_WEB_SMOKE_HEADING_TEXT",
                        props.getProperty("smoke.headingText"), DEFAULT_HEADING_TEXT),
                resolve("web.smoke.itemSelector", "QAFF_WEB_SMOKE_ITEM_SELECTOR",
                        props.getProperty("smoke.itemSelector"), DEFAULT_ITEM_SELECTOR));
    }

    private static String resolve(String sysProp, String envVar, String fileValue, String fallback) {
        String sys = System.getProperty(sysProp);
        if (sys != null && !sys.isBlank()) {
            return sys.trim();
        }
        String env = System.getenv(envVar);
        if (env != null && !env.isBlank()) {
            return env.trim();
        }
        if (fileValue != null && !fileValue.isBlank()) {
            return fileValue.trim();
        }
        return fallback;
    }
}
