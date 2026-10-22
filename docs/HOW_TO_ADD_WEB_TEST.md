# How to add a web test

## Overview

Web tests use Selenium 4 and JUnit 5. This framework is **not** Spring-based:
there is no dependency injection. Tests extend `BaseWebTest`, which boots a
`WebDriver` for each test from `config.properties` and captures a screenshot on
failure. This guide shows you how to add a new test.

## Steps

### 1. Create a new test class

Add a class under `apps/<id>/web-desktop/src/test/java/` (next to the generated
`DemoHomeSmokeTest`). Extend `BaseWebTest` to inherit the `driver` and `timeout`
fields and the offline-fixture URL helper:

```java
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.clientdemopublicqa.web.test.BaseWebTest;

@Epic("Checkout")
@Feature("Cart")
class MyWebTest extends BaseWebTest {

  @Test
  @DisplayName("Home page renders")
  void homePageRenders() {
    // Offline against the bundled fixture; see HOW_TO_TARGET_YOUR_APP.md to
    // run against your own application instead.
    String url = resolveSmokeUrl("fixtures/<id>.html");
    driver.get(url);
    // ... your assertions, ideally via a page object (step 2)
  }
}
```

`BaseWebTest` exposes `driver`, `timeout` (a `Duration` from
`config.properties`), and `resolveSmokeUrl(...)`. Do not create or quit the
driver yourself — the base class manages its lifecycle.

### 2. Add locators and page objects

Encapsulate element locators in a page object that extends `BasePage`. See
[HOW_TO_ADD_PAGE_OBJECT.md](HOW_TO_ADD_PAGE_OBJECT.md) and the generated
`DemoHomePage` / `ProductCardComponent` for working examples.

### 3. Use explicit waits

Never use `Thread.sleep()`. `BasePage` already provides `waitForVisible`,
`waitForClickable`, `waitForInvisible`, `waitAndClick`, `waitAndType`,
`selectByVisibleText`, `hover`, `getText`, and `executeScript`. If you need a
wait in a test directly, build one from the inherited `timeout`:

```java
WebDriverWait wait = new WebDriverWait(driver, timeout);
wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='submit']")));
```

### 4. Add Allure annotations

Document your test for reporting (`@Epic`/`@Feature` on the class,
`@Story`/`@Severity` on the method):

```java
@Story("User authentication")
@Severity(SeverityLevel.CRITICAL)
@Test
@DisplayName("User can sign in")
void userCanSignIn() { ... }
```

See [HOW_TO_USE_ALLURE.md](HOW_TO_USE_ALLURE.md) for details.

### 5. Run locally

```bash
mvn -pl apps/<id>/web-desktop test
```

See [HOW_TO_RUN_TESTS_LOCALLY.md](HOW_TO_RUN_TESTS_LOCALLY.md) for more options.

## Examples

In `apps/<id>/web-desktop/src/test/java`:

- `DemoHomeSmokeTest` — the minimal runnable smoke test.
- `DemoProductCardTest` — a richer example: a reusable `PageComponent`, soft
  assertions, and a parameterized test.
- `ExampleWebTest` — an annotated `@Disabled` template you can copy.
