# framework-mobile

Shared Appium driver factory and base mobile test class. Mobile Appium layer.

## Contents

- `AppiumDriverFactory` — creates an `AppiumDriver` from
  `appium-config.properties` on the test-resources classpath.
- `BaseMobileTest` — JUnit 5 base class that opens/closes the driver
  around each test.

## Dependencies

`framework-mobile` depends only on `framework-core` (config loader) and
`io.appium:java-client`. It does **not** depend on `framework-web` or
any Selenium WebDriver classes.

## Configuration

Each per-app `mobile-appium` / `android` / `ios` module ships with
`src/test/resources/appium-config.properties`. Android sessions use typed
`UiAutomator2Options` (Appium 2/3 + the `uiautomator2` driver), iOS sessions typed
`XCUITestOptions` (the `xcuitest` driver, macOS + Xcode). Every key can be
overridden with `-D<key>` or a `QAFF_<KEY>` environment variable (for example
`QAFF_APPIUM_APP`, `QAFF_APPIUM_UDID`). The smoke tests run only with
`-Dappium.enabled=true`, which the `scripts/run-*-smoke` helpers set, so a plain
`mvn verify` without a device or simulator skips them.

## Dependency direction

```
apps/<id>/mobile-appium  ──►  framework-mobile  ──►  framework-core
```
