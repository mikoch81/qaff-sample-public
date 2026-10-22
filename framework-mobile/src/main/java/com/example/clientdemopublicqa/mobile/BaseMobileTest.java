package com.example.clientdemopublicqa.mobile;

import io.appium.java_client.AppiumDriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for Appium mobile tests.
 * Subclasses call {@link #driver()} to access the session.
 *
 * <p>The driver is created by {@link AppiumDriverFactory} using
 * {@code appium-config.properties} from the test-resources classpath.
 * Gate subclasses with {@code @EnabledIfSystemProperty(named = "appium.enabled",
 * matches = "true")} so a plain {@code mvn verify} without a device stays green.
 */
public abstract class BaseMobileTest {

    private AppiumDriver appiumDriver;

    @BeforeEach
    void openDriver() {
        appiumDriver = AppiumDriverFactory.create("appium-config.properties");
    }

    @AfterEach
    void closeDriver() {
        if (appiumDriver != null) {
            appiumDriver.quit();
        }
    }

    protected AppiumDriver driver() {
        return appiumDriver;
    }
}
