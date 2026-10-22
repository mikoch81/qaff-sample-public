# framework-api

Shared RestAssured utilities for every API smoke-test module in this project.
Part of the **Quality Impact Pack**.

## What's here

```
framework-api/
├── pom.xml                              # packaging=jar, depends on framework-core + rest-assured
└── src/main/java/.../api/
    ├── ApiConfig.java                   # properties-based config record
    └── ApiTestSupport.java              # shared RequestSpecification builder
```

## Dependency direction

```
apps/<id>/api  →  framework-api  →  framework-core
```

Each `apps/<id>/api` module declares `framework-api` as a `test`-scope
dependency. `framework-api` declares `framework-core` and `rest-assured`
as compile-scope dependencies so both are available transitively to the
app-level test modules.

## Usage

```java
import com.example.<project>.api.ApiConfig;
import com.example.<project>.api.ApiTestSupport;

ApiConfig cfg = ApiConfig.load("api-config.properties");
Response r = ApiTestSupport.baseSpec(cfg.baseUrl())
        .when().get(cfg.healthPath());
```

See `HealthApiSmokeTest.java` in any `apps/<id>/api` module: with the default
`api.baseUrl=local` it starts an in-process `HttpServer` stub (offline); with a
real base URL it calls your service's `api.healthPath` instead.

API-only Maven execution is performed by selecting the target module
explicitly, for example: `mvn -B -pl apps/<id>/api -am verify`.

## Runtime overrides

`ApiConfig.load("api-config.properties")` resolves `api.baseUrl`,
`api.timeoutSeconds` and `api.healthPath` in this order (highest first):

1. JVM system property: `-Dapi.baseUrl=...` / `-Dapi.timeoutSeconds=...` / `-Dapi.healthPath=...`
2. Environment variable: `QAFF_API_BASE_URL` / `QAFF_API_TIMEOUT_SECONDS` / `QAFF_API_HEALTH_PATH`
3. Value declared in the loaded properties file
4. Built-in default (`local` / `10` seconds / `/health`)

This lets the same generated smoke run fully offline by default and against a
real environment when overrides are set, without changing test code.

## Local mock target

To rehearse the real-target path without a deployed service, run the bundled
dependency-free mock (JDK single-file program) and point the smoke at it:

```bash
bash scripts/run-api-mock.sh 8089 &        # serves GET /health -> {"status":"ok"}
QAFF_API_BASE_URL=http://127.0.0.1:8089 bash scripts/run-api-smoke.sh
```
