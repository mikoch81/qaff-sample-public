package com.example.clientdemopublicqa.apps.demoshop.ios;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.Properties;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.example.clientdemopublicqa.mobile.AppiumDriverFactory;

/**
 * iOS native Appium smoke test for demo-shop.
 *
 * <p>Runs only with {@code -Dappium.enabled=true} (set by the run-*-smoke
 * scripts), so {@code mvn verify} without a device skips it. Needs a running
 * Appium server with the XCUITest driver and a booted simulator or device; see
 * this module's README. Values come from {@code appium-config.properties},
 * each overridable by {@code -D<key>} or {@code QAFF_<KEY>} (e.g.
 * {@code QAFF_APPIUM_APP}, {@code QAFF_SMOKE_ACCESSIBILITY_ID}).
 */
@EnabledIfSystemProperty(named = "appium.enabled", matches = "true",
        disabledReason = "Appium smoke runs only with -Dappium.enabled=true (scripts/run-*-smoke set it)")
class IosSmokeTest {

    private static final String CONFIG = "appium-config.properties";

    @Test
    void appLaunchesAndShowsExpectedElement() {
        Properties config = AppiumDriverFactory.loadConfig(CONFIG);
        String expected = AppiumDriverFactory.resolve(config, "smoke.accessibilityId", "");
        long waitSeconds = Long.parseLong(AppiumDriverFactory.resolve(config, "smoke.timeoutSeconds", "30"));

        AppiumDriver driver = AppiumDriverFactory.create(CONFIG);
        try {
            assertNotNull(driver.getSessionId(), "Appium session should be established");
            if (!expected.isEmpty()) {
                new WebDriverWait(driver, Duration.ofSeconds(waitSeconds))
                        .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(expected)));
            }
        } finally {
            driver.quit();
        }
    }
}
