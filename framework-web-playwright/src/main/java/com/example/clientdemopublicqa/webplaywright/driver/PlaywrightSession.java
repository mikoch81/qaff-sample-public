package com.example.clientdemopublicqa.webplaywright.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * One browser session: the Playwright engine, a launched {@link Browser},
 * one {@link BrowserContext} and its {@link Page}. Closing the session
 * releases everything, engine included.
 *
 * <p>Playwright objects are not thread-safe; a session must stay on the
 * thread that created it. {@code BaseWebTest} and {@code BaseWebMobileTest}
 * create one session per test, which keeps the default Surefire execution
 * model safe.
 */
public final class PlaywrightSession implements AutoCloseable {

    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;

    PlaywrightSession(Playwright playwright, Browser browser, BrowserContext context, Page page) {
        this.playwright = playwright;
        this.browser = browser;
        this.context = context;
        this.page = page;
    }

    public Page page() {
        return page;
    }

    @Override
    public void close() {
        // Close in reverse creation order; each step is defensive so a failure
        // in one close never leaks the underlying driver process.
        closeQuietly(() -> {
            if (!page.isClosed()) {
                page.close();
            }
        });
        closeQuietly(context::close);
        closeQuietly(browser::close);
        closeQuietly(playwright::close);
    }

    private static void closeQuietly(Runnable close) {
        try {
            close.run();
        } catch (RuntimeException e) {
            System.err.println("[PlaywrightSession] Ignored close failure: " + e.getMessage());
        }
    }
}
