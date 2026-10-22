# How to point tests at your own app

By default the generated web smoke tests run **offline** against a bundled local
HTML fixture (no network). When you are ready to test your own application, switch
the target — no code change required.

## The two targets

- `fixture` (default) — loads the bundled `fixtures/<app>.html` over a `file://` URL.
- `external` — loads a real base URL you provide.

## Option A — edit `config.properties`

Each web app reads `apps/<id>/web-desktop/src/test/resources/config.properties`:

```properties
browser=chrome
headless=true
target=external
baseUrl=https://your-app.example.com
timeoutSeconds=10
smoke.headingSelector=h1
smoke.headingText=Welcome
smoke.itemSelector=nav a
```

Set `target=external` and `baseUrl` to your application's URL, and the three
`smoke.*` keys to elements that exist on its home page: the smoke test waits for
`smoke.headingSelector`, checks its text equals `smoke.headingText`, and requires at
least one element matching `smoke.itemSelector`. The defaults describe the bundled
fixture. (The same keys can be set once in the qaff config as `apps[].web.smoke`.)

The richer `DemoProductCardTest` reference example asserts the fixture's own product
data, so it is skipped automatically on an external target.

## Option B — override at run time (no file edit)

Settings resolve in this order (**highest wins**):

1. System property (e.g. `-Dweb.baseUrl=…`)
2. Environment variable (e.g. `QAFF_WEB_BASE_URL=…`)
3. `config.properties`
4. Built-in default

| Setting | System property | Environment variable | `config.properties` |
| --- | --- | --- | --- |
| Target | `-Dweb.target` | `QAFF_WEB_TARGET` | `target` |
| Base URL | `-Dweb.baseUrl` | `QAFF_WEB_BASE_URL` | `baseUrl` |
| Browser | `-Dbrowser` | `QAFF_BROWSER` | `browser` |
| Heading selector | `-Dweb.smoke.headingSelector` | `QAFF_WEB_SMOKE_HEADING_SELECTOR` | `smoke.headingSelector` |
| Heading text | `-Dweb.smoke.headingText` | `QAFF_WEB_SMOKE_HEADING_TEXT` | `smoke.headingText` |
| Item selector | `-Dweb.smoke.itemSelector` | `QAFF_WEB_SMOKE_ITEM_SELECTOR` | `smoke.itemSelector` |

Supported browsers: `chrome` (default), `firefox`, `edge`.

## Worked example

Run the existing smoke against your app in Firefox, without editing any file:

```bash
mvn -pl apps/<id>/web-desktop test \
  -Dweb.target=external \
  -Dweb.baseUrl=https://your-app.example.com \
  -Dbrowser=firefox
```

> If `target=external` but no `baseUrl` is configured, the test fails fast with a
> clear message — set one of the values above. Tests you write against the bundled
> fixture's elements will need selectors that match **your** application; see
> [locator-policy.md](locator-policy.md) and [HOW_TO_ADD_WEB_TEST.md](HOW_TO_ADD_WEB_TEST.md).

## Public demo targets

The generator ships two example configs that point at public demo sites with the
matching `smoke.*` values (`examples/configs/demo-web-the-internet.yaml`,
`examples/configs/demo-web-saucedemo.yaml`). They need network access and may break if
the third-party site changes; use them to rehearse the external-target flow.

## API layer (Quality Impact Pack)

`apps/<id>/api/src/test/resources/api-config.properties` works the same way:
`api.baseUrl=local` (default) starts an in-process stub so the smoke is offline; any
other value — or `-Dapi.baseUrl` / `QAFF_API_BASE_URL` — runs the same test against
your service's `api.healthPath` (default `/health`). To rehearse without a deployed
service, start the bundled mock first: `bash scripts/run-api-mock.sh 8089 &` then
`QAFF_API_BASE_URL=http://127.0.0.1:8089 bash scripts/run-api-smoke.sh`.
`qaff doctor --project .` reports whether configured external web targets answer.
