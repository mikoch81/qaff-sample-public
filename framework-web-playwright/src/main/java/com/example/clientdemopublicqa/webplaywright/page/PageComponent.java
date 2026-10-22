package com.example.clientdemopublicqa.webplaywright.page;

import java.time.Duration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * Base class for reusable page components (modals, navigation bars, sidebars,
 * etc.) that are scoped to a container locator resolved by the caller.
 *
 * <pre>{@code
 * public class LoginModal extends PageComponent {
 *     private static final String SUBMIT_BTN = "#submit";
 *
 *     public LoginModal(Locator container, Duration timeout) {
 *         super(container, timeout);
 *     }
 *
 *     @Step("Submit login form")
 *     public void submitLogin(String username, String password) {
 *         waitForVisible(SUBMIT_BTN).click();
 *     }
 * }
 * // Usage in a page (pass the same timeout your BaseWebTest / pages use):
 * Locator modal = page.locator("#login-modal");
 * new LoginModal(modal, Duration.ofSeconds(timeoutSeconds)).submitLogin("user", "pass");
 * }</pre>
 */
public abstract class PageComponent {

    /** Fallback wait used by the one-argument compatibility constructor. */
    protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    protected final Locator container;
    protected final Duration timeout;

    /**
     * Preferred constructor — pass the same timeout the rest of the framework
     * uses (the configured {@code timeoutSeconds}), so components never wait
     * with a different budget than pages.
     */
    protected PageComponent(Locator container, Duration timeout) {
        this.container = container;
        this.timeout = timeout;
    }

    /** Compatibility constructor using {@link #DEFAULT_TIMEOUT}. */
    protected PageComponent(Locator container) {
        this(container, DEFAULT_TIMEOUT);
    }

    /**
     * Waits until an element matching {@code selector} <em>inside this
     * component's container</em> is visible, and returns it.
     *
     * <p>The lookup is chained off {@link #container} — a match elsewhere on
     * the page (e.g. the first of several cards) is never returned. Do not
     * replace this with a page-level lookup, which resolves against the whole
     * page instead of this component.
     */
    protected Locator waitForVisible(String selector) {
        Locator locator = container.locator(selector).first();
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout.toMillis()));
        return locator;
    }

    protected boolean isDisplayed() {
        try {
            return container.isVisible();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
