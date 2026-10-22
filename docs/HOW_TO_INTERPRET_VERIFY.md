# How to interpret qaff verify output

## Overview

`qaff verify --project <dir>` performs structural health checks on your generated framework. This guide explains the output.

## Running verify

```bash
cd generated-project
qaff verify --project .
```

## What verify checks

### Dependency Direction

Ensures `framework-*` modules do not depend on `apps/*` modules. This enforces a clean architecture where:
- `framework-core`, `framework-web` are shared and reusable
- `apps/*` depend on framework modules
- `apps/*` may not be shared between applications

Example error:

```
❌ Dependency violation: apps/myapp/web-desktop → framework-web (not allowed)
```

### Managed Files

Verifies that managed files (files generated and managed by `qaff`) have not been manually edited. Tampering breaks `qaff update`.

Example error:

```
⚠️  Managed file modified: pom.xml (hash mismatch)
```

### Module Structure

Ensures all expected modules exist and are correctly referenced in `pom.xml`.

## Output Interpretation

- ✅ Green checkmark = check passed
- ❌ Red X = check failed (fix required)
- ⚠️  Yellow warning = check passed with caution

## Next Steps

If verify fails, see [GENERATED_FILE_OWNERSHIP.md](GENERATED_FILE_OWNERSHIP.md) for guidance on which files are safe to modify.
