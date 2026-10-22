package com.example.clientdemopublicqa.webplaywright.driver;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * Creates {@link PlaywrightSession} instances (Playwright engine + browser +
 * context + page) for tests. Playwright downloads its bundled browsers
 * automatically on the first run (network required once); set
 * {@code PLAYWRIGHT_BROWSERS_PATH} to relocate that cache.
 *
 * Browser is selected at runtime via the {@code QAFF_BROWSER} environment
 * variable or the {@code browser} system property / config.properties key
 * (default: {@code chromium}). Supported values: {@code chromium},
 * {@code firefox}, {@code webkit}. Note: webkit needs extra system libraries
 * on Linux — the generated smoke defaults to chromium.
 *
 * For chromium: override the browser binary with the {@code BROWSER_BINARY}
 * environment variable (e.g. {@code /usr/bin/chromium}) to use a
 * system-installed browser instead of the bundled one.
 */
public final class PlaywrightFactory {

    private PlaywrightFactory() {
    }

    public static PlaywrightSession launch(String browser, boolean headless) {
        String normalized = browser == null ? "chromium" : browser.trim().toLowerCase();
        Playwright playwright = Playwright.create();
        try {
            BrowserType browserType = switch (normalized) {
                case "chromium", "chrome" -> playwright.chromium();
                case "firefox" -> playwright.firefox();
                case "webkit" -> playwright.webkit();
                default -> throw new IllegalArgumentException(
                        "Unsupported browser '" + browser
                                + "'. Supported: chromium, firefox, webkit.");
            };

            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                    .setHeadless(headless);
            String binary = System.getenv("BROWSER_BINARY");
            if (binary != null && !binary.isBlank()
                    && (normalized.equals("chromium") || normalized.equals("chrome"))) {
                options.setExecutablePath(Paths.get(binary.trim()));
            }

            Browser launched = browserType.launch(options);
            BrowserContext context = launched.newContext(
                    new Browser.NewContextOptions().setViewportSize(1280, 800));
            Page page = context.newPage();
            return new PlaywrightSession(playwright, launched, context, page);
        } catch (RuntimeException e) {
            // Launch failed after Playwright.create() — do not leak the driver process.
            playwright.close();
            throw e;
        }
    }

    /**
     * Launches Chromium with mobile-device emulation: the given viewport,
     * device scale factor and user agent, with touch and mobile mode enabled.
     * Mirrors the Selenium engine's {@code WebDriverFactory.newChromeMobile} —
     * emulation is Chromium-only (Playwright does not support
     * {@code isMobile} on Firefox), so the {@code browser} config key is
     * ignored for the web-mobile layer. No real device, Appium server or
     * network is required — this is a desktop Chromium running a mobile
     * viewport. {@code BROWSER_BINARY} still overrides the browser binary.
     */
    public static PlaywrightSession launchMobile(
            boolean headless, int width, int height, double pixelRatio, String userAgent) {
        Playwright playwright = Playwright.create();
        try {
            BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                    .setHeadless(headless);
            String binary = System.getenv("BROWSER_BINARY");
            if (binary != null && !binary.isBlank()) {
                options.setExecutablePath(Paths.get(binary.trim()));
            }

            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                    .setViewportSize(width, height)
                    .setDeviceScaleFactor(pixelRatio)
                    .setIsMobile(true)
                    .setHasTouch(true);
            if (userAgent != null && !userAgent.isBlank()) {
                contextOptions.setUserAgent(userAgent);
            }

            Browser launched = playwright.chromium().launch(options);
            BrowserContext context = launched.newContext(contextOptions);
            Page page = context.newPage();
            return new PlaywrightSession(playwright, launched, context, page);
        } catch (RuntimeException e) {
            // Launch failed after Playwright.create() — do not leak the driver process.
            playwright.close();
            throw e;
        }
    }
}
