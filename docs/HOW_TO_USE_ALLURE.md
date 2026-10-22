# How to use Allure

## Overview

Allure generates beautiful, interactive test reports. This guide shows you how to annotate your tests for better reports.

## Key Annotations

### @Feature, @Story, @Severity

Organize and categorize tests:

```java
@Severity(SeverityLevel.CRITICAL)
@Feature("User Authentication")
@Story("Login with valid credentials")
@Test
void userCanLogin() { ... }
```

### @DisplayName

Human-readable test names:

```java
@Test
@DisplayName("User can login with valid email and password")
void userCanLogin() { ... }
```

### Step annotations

Break down test steps for clarity:

```java
@Test
void userCanLogin() {
  step("Navigate to login page");
  driver.get(baseUrl);
  
  step("Enter credentials");
  loginPage.enterEmail("user@example.com");
  loginPage.enterPassword("password123");
  
  step("Submit and verify success");
  loginPage.clickSubmit();
  assertThat(driver.getTitle()).contains("Dashboard");
}

private void step(String message) {
  // Allure step marker or custom logger
  System.out.println("→ " + message);
}
```

## View Reports

After running tests, raw results are in each module's `target/allure-results/`.
The root `pom.xml` manages the `allure-maven` plugin, so the `allure:` goals
resolve from any module:

```bash
# One HTML report for the whole reactor (target/site/allure-maven-plugin/index.html)
bash scripts/allure-report.sh

# A single module: render or serve its own results
mvn -pl apps/<app>/web-desktop allure:report
mvn -pl apps/<app>/web-desktop allure:serve
```

## Best Practices

1. **Use meaningful severity levels** — `CRITICAL` for must-pass, `MINOR` for edge cases.
2. **Group by feature and story** — Makes it easy to find related tests.
3. **Document step names** — Each step should be human-readable.
