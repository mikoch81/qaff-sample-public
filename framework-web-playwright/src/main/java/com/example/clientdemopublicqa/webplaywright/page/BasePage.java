package com.example.clientdemopublicqa.webplaywright.page;

import java.time.Duration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;

/**
 * Page Object base class for the Playwright engine. Locators are CSS selector
 * strings resolved with {@link Page#locator(String)}; waits are Playwright's
 * built-in auto-waiting plus explicit {@link Locator#waitFor} where a helper
 * must block until a state is reached.
 * Each interaction method is annotated with {@link Step} so it appears as a
 * named step in the Allure report.
 */
public abstract class BasePage {

    protected final Page page;
    protected final Duration timeout;

    protected BasePage(Page page, Duration timeout) {
        this.page = page;
        this.timeout = timeout;
    }

    @Step("Navigate to {url}")
    protected void navigate(String url) {
        page.navigate(url);
    }

    @Step("Wait for visible: {selector}")
    protected Locator waitForVisible(String selector) {
        Locator locator = page.locator(selector).first();
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout.toMillis()));
        return locator;
    }

    @Step("Click: {selector}")
    protected void waitAndClick(String selector) {
        waitForVisible(selector).click();
    }

    @Step("Type '{text}' into {selector}")
    protected void waitAndType(String selector, String text) {
        waitForVisible(selector).fill(text);
    }

    @Step("Select '{text}' in {selector}")
    protected void selectByVisibleText(String selector, String text) {
        waitForVisible(selector).selectOption(new SelectOption().setLabel(text));
    }

    @Step("Hover over {selector}")
    protected void hover(String selector) {
        waitForVisible(selector).hover();
    }

    @Step("Wait for hidden: {selector}")
    protected void waitForHidden(String selector) {
        page.locator(selector).first().waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeout.toMillis()));
    }

    @Step("Get text of {selector}")
    protected String getText(String selector) {
        // innerText() returns rendered text — the closest analog of
        // Selenium's WebElement#getText().
        return waitForVisible(selector).innerText();
    }

    /**
     * Execute arbitrary JavaScript in the current page context.
     * Convenience wrapper around {@link Page#evaluate(String, Object)}.
     */
    protected Object executeScript(String script, Object arg) {
        return page.evaluate(script, arg);
    }

    /** Return the current page title. */
    protected String getTitle() {
        return page.title();
    }

    /** Return the current page URL. */
    protected String getCurrentUrl() {
        return page.url();
    }
}
