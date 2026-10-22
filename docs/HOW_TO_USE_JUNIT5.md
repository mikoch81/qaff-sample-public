# How to use JUnit 5

## Overview

JUnit 5 (Jupiter) is the test framework. This guide covers common features.

## Tags

Organize tests with tags for selective execution:

```java
@Test
@Tag("smoke")
@Tag("critical")
void userCanLogin() { ... }

@Test
@Tag("slow")
void complexReportGeneration() { ... }
```

Run specific tags:

```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=smoke,critical
mvn test -DexcludedGroups=slow
```

## Parameterized Tests

Run the same test with different inputs:

```java
@ParameterizedTest
@ValueSource(strings = { "user@example.com", "admin@example.com" })
void userCanLoginWithDifferentEmails(String email) {
  loginPage.enterEmail(email);
  loginPage.submitAndVerify();
}
```

## Display Names

Human-readable test names:

```java
@Test
@DisplayName("User can login with valid credentials")
void login() { ... }
```

## Nested Test Classes

Organize complex test suites:

```java
@DisplayName("Login feature")
class LoginTest {
  @Nested
  @DisplayName("Valid credentials")
  class ValidCredentials {
    @Test
    void userCanLogin() { ... }
  }

  @Nested
  @DisplayName("Invalid credentials")
  class InvalidCredentials {
    @Test
    void userCannotLogin() { ... }
  }
}
```

## Best Practices

1. **One assertion per test** — Or group related assertions with `assertAll()`.
2. **Clear method names** — Or use `@DisplayName`.
3. **Use tags strategically** — Group by smoke, regression, slow, etc.
