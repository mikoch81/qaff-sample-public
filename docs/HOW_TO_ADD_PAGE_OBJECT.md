# How to add a page object

## Overview

Page Object Model (POM) encapsulates page structure and selectors. This reduces duplication and makes tests more maintainable.

## Structure

Create a class for each logical page, extending `BasePage` from
`framework-web-playwright`:

```java
public class LoginPage extends BasePage {

  // Selectors (CSS strings resolved via Page.locator)
  private static final String EMAIL_INPUT = "#email";
  private static final String PASSWORD_INPUT = "#password";
  private static final String SUBMIT_BUTTON = "[data-testid='login-submit']";

  public LoginPage(Page page, Duration timeout) {
    super(page, timeout);
  }

  // Page actions
  public void enterEmail(String email) {
    waitForVisible(EMAIL_INPUT);
    page.locator(EMAIL_INPUT).fill(email);
  }

  public void enterPassword(String password) {
    page.locator(PASSWORD_INPUT).fill(password);
  }

  public void clickSubmit() {
    page.locator(SUBMIT_BUTTON).click();
  }
}
```

## Best Practices

1. **One responsibility per class** — Each page object handles one logical page or component.
2. **Selectors at the top** — Group all selector constants together.
3. **Waits before interactions** — Use `waitForVisible(selector)` before the first interaction; Playwright actions then auto-wait.
4. **Return page objects** — Navigation methods should return the next page object for fluent chains.

## Example

See the generated `DemoHomePage.java` under `apps/<app-id>/web-desktop/src/test/java` for a complete, working template.
