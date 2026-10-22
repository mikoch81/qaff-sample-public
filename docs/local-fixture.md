# Local fixture

The web-desktop smoke tests do **not** point at any public website. Each
app opens its own static HTML file bundled inside its test resources:

- `apps/demo-shop/web-desktop/src/test/resources/fixtures/demo-shop.html`

## How it is loaded

`ResourcePath.classpathFileUrl("fixtures/<app-id>.html")` resolves the
file through the JVM classloader and returns a `file://...` URL. The
test then calls `driver.get(url)`. No HTTP, no DNS, no third-party
servers. Example for `demo-shop`:

```java
String url = ResourcePath.classpathFileUrl("fixtures/demo-shop.html");
```

## Why a fixture and not SauceDemo

- Deterministic markup — tests cannot break when an external page changes.
- Zero network requirements — the tests run the same on a developer
  laptop, in CI and behind restricted firewalls.
- Neutral domain — the framework intentionally avoids any real brand.

## Stable selectors

Each fixture exposes `data-testid` attributes (`store-heading`,
`product-card`, `product-name`, `product-price`, `add-to-cart`)
so page objects can use stable selectors without depending on layout
classes.
