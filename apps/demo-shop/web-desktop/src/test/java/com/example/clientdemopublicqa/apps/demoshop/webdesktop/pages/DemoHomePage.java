package com.example.clientdemopublicqa.apps.demoshop.webdesktop.pages;

import java.time.Duration;

import com.microsoft.playwright.Page;

import com.example.clientdemopublicqa.webplaywright.config.SmokeExpectations;
import com.example.clientdemopublicqa.webplaywright.page.BasePage;

/**
 * Home page of the smoke target. The elements it looks for come from
 * {@link SmokeExpectations} (config.properties {@code smoke.*} keys), so the
 * same page object works for the bundled fixture and for your real application.
 */
public class DemoHomePage extends BasePage {

    private final String heading;
    private final String item;

    public DemoHomePage(Page page, Duration timeout) {
        this(page, timeout, SmokeExpectations.defaults());
    }

    public DemoHomePage(Page page, Duration timeout, SmokeExpectations smoke) {
        super(page, timeout);
        this.heading = smoke.headingSelector();
        this.item = smoke.itemSelector();
    }

    public DemoHomePage open(String url) {
        navigate(url);
        waitForVisible(heading);
        return this;
    }

    public String headingText() {
        return getText(heading);
    }

    public int itemCount() {
        return page.locator(item).count();
    }
}
