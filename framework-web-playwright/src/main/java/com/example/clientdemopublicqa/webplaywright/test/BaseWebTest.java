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
 * Boots one Playwright browser session ({@link Page}) for each test using
 * values from {@code config.properties} on the test classpath. Playwright
 * downloads its bundled browsers automatically on the first run (network
 * required once).
 *
 * <p>Browser selection order (highest wins):
 * <ol>
 *   <li>System property {@code -Dbrowser=firefox}</li>
 *   <li>Environment variable {@code QAFF_BROWSER=firefox}</li>
 *   <li>{@code browser=chromium} in {@code config.properties}</li>
 *   <li>Default: {@code chromium}</li>
 * </ol>
 * Supported values: {@code chromium}, {@code firefox}, {@code webkit}.
 *
 * <p>Failed tests automatically capture a screenshot via
 * {@link ScreenshotOnFailureExtension} — saved to
 * {@code target/screenshots/<ClassName>/<method>-<nano>.png}.
 */
@ExtendWith(ScreenshotOnFailureExtension.class)
public abstract class BaseWebTest {

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
        String browser = resolveBrowser(config);
        session = PlaywrightFactory.launch(browser, headless);
        page = session.page();
        page.setDefaultTimeout(timeout.toMillis());
    }

    private static String resolveBrowser(Properties config) {
        String sysprop = System.getProperty("browser");
        if (sysprop != null && !sysprop.isBlank()) return sysprop.trim();
        String env = System.getenv("QAFF_BROWSER");
        if (env != null && !env.isBlank()) return env.trim();
        return config.getProperty("browser", "chromium");
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
