# How to keep dependency direction valid

## Overview

The generated framework enforces a dependency direction rule to maintain clean architecture. This guide explains the rule and how to follow it.

## The Rule

**Framework modules may not depend on application modules.**

Allowed:
- `apps/<id>/*` may depend on `framework-*`
- `apps/<id>/<layer>` may depend on `apps/<id>/framework-*`

Not allowed:
- `framework-*` may not import from `apps/*`
- `apps/id-a` may not import from `apps/id-b`

## Why This Matters

This rule keeps the framework reusable across multiple applications. If the framework depends on a specific app, it becomes coupled to that app and cannot be shared.

## Checking Violations

Run `qaff verify --project .` to check for violations:

```bash
qaff verify --project .
```

Maven checks the same rule on every build: `mvn validate` (and therefore
`mvn verify`) runs `maven-enforcer-plugin` (execution
`enforce-dependency-direction`), which fails with "Dependency direction
violated in <module>" when a POM declares a forbidden internal dependency.

Example error output:

```
❌ Dependency violation detected:
   framework-web → apps/myapp/web-desktop
   Reason: framework modules may not depend on app modules
```

## How to Fix

If you get a violation, refactor your code:

1. **Move shared code to framework** — If the code is needed by multiple tests or multiple apps, move it to `framework-web/src/test/java`.

2. **Move app-specific code to app** — If the code is specific to one app, keep it in `apps/<id>/web-desktop/src/test/java`.

3. **Use a static utility** — If you need constants or helper methods in both, define them in the framework.

Example structure:

```
framework-web/src/test/java/
├── com/example/test/
│   ├── page/           ← Shared page objects
│   ├── util/           ← Shared utilities
│   └── BaseTest.java   ← Base class for all tests

apps/myapp/web-desktop/src/test/java/
└── com/example/myapp/test/
    ├── LoginTest.java     ← MyApp-specific tests
    └── page/MyAppPage.java ← MyApp-specific page objects (extends framework BaseTest)
```

## Best Practices

1. **Keep shared code in framework** — Page objects, base classes, utilities
2. **Keep app-specific code in apps** — Test classes, app-specific fixtures
3. **Run verify regularly** — Catch violations early
4. **Use clear naming** — `BasePageObject`, `AppConfig` signal intent
