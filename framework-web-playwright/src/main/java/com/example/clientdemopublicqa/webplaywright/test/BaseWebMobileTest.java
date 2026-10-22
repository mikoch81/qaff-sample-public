package com.example.clientdemopublicqa.webplaywright.test;

import java.time.Duration;
import java.util.Properties;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import com.example.clientdemopublicqa.core.config.ConfigLoader;
import com.example.clientdemopublicqa.core.util.ResourcePath;
import com.example.clientdemopublicqa.webplaywright.config.SmokeExpectations;
import com.example.clientdemopublicqa.webplaywright.config.WebConfig;
import com.example.clientdemopublicqa.webplaywright.driver.PlaywrightFactory;
import com.example.clientdemopublicqa.webplaywright.driver.PlaywrightSession;
import com.example.clientdemopublicqa.webplaywright.extension.ScreenshotOnFailureExtension;

/**
 * Boots one Chromium Playwright session ({@link Page}) under mobile-device
 * emulation for each test, using device metrics from
 * {@code config.properties} on the test classpath ({@code mobile.width},
 * {@code mobile.height}, {@code mobile.pixelRatio}, {@code mobile.userAgent}).
 * No Appium server or real device is required. Emulation is Chromium-only,
 * so the {@code browser} key is ignored for this layer.
 *
 * <p>Failed tests automatically capture a screenshot via
 * {@link ScreenshotOnFailureExtension} — saved to
 * {@code target/screenshots/<ClassName>/<method>-<nano>.png}.
 */
@ExtendWith(ScreenshotOnFailureExtension.class)
public abstract class BaseWebMobileTest {

    protected Page page;
    protected Properties config;
    protected Duration timeout;
    protected WebConfig webConfig;
    protected SmokeExpectations smoke;

    private PlaywrightSession session;

    @BeforeEach
    void startBrowser() {
        config = ConfigLoader.fromClasspath("config.properties");
        boolean headless = Boolean.parseBoolean(config.getProperty("headless", "true"));
        long timeoutSeconds = Long.parseLong(config.getProperty("timeoutSeconds", "10"));
        timeout = Duration.ofSeconds(timeoutSeconds);
        webConfig = WebConfig.from(config);
        smoke = SmokeExpectations.from(config);

        int width = Integer.parseInt(config.getProperty("mobile.width", "390"));
        int height = Integer.parseInt(config.getProperty("mobile.height", "844"));
        double pixelRatio = Double.parseDouble(config.getProperty("mobile.pixelRatio", "3.0"));
        String userAgent = config.getProperty("mobile.userAgent", "");

        session = PlaywrightFactory.launchMobile(headless, width, height, pixelRatio, userAgent);
        page = session.page();
        page.setDefaultTimeout(timeout.toMillis());
    }

    protected String resolveSmokeUrl(String fixtureClasspathName) {
        if (WebConfig.TARGET_FIXTURE.equals(webConfig.target())) {
            return ResourcePath.classpathFileUrl(fixtureClasspathName);
        }

        String baseUrl = webConfig.baseUrl();
        if (baseUrl == null
                || baseUrl.isBlank()
                || WebConfig.LOCAL_FIXTURE_SENTINEL.equalsIgnoreCase(baseUrl.trim())) {
            throw new IllegalStateException(
                    "External web target selected but baseUrl is not configured. "
                            + "Set -Dweb.baseUrl, QAFF_WEB_BASE_URL, or baseUrl in config.properties.");
        }

        return baseUrl.trim();
    }

    @AfterEach
    void stopBrowser() {
        if (session != null) {
            session.close();
            session = null;
            page = null;
        }
    }
}
