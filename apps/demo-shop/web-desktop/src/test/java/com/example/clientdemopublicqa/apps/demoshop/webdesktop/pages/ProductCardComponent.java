package com.example.clientdemopublicqa.apps.demoshop.webdesktop.pages;

import java.time.Duration;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;

import com.example.clientdemopublicqa.webplaywright.page.PageComponent;

/**
 * Reusable component scoped to a single product card
 * ({@code [data-testid='product-card']}). Demonstrates the {@link PageComponent}
 * pattern: child elements are resolved relative to the component's container,
 * so the same component works for any card on the page.
 */
public class ProductCardComponent extends PageComponent {

    private static final String NAME = "[data-testid='product-name']";
    private static final String PRICE = "[data-testid='product-price']";
    private static final String ADD_TO_CART = "[data-testid='add-to-cart']";

    public ProductCardComponent(Locator container, Duration timeout) {
        super(container, timeout);
    }

    @Step("Read product name")
    public String productName() {
        return container.locator(NAME).innerText();
    }

    @Step("Read product price")
    public String priceText() {
        return container.locator(PRICE).innerText();
    }

    @Step("Check the add-to-cart button is available")
    public boolean canAddToCart() {
        return container.locator(ADD_TO_CART).isVisible();
    }
}
