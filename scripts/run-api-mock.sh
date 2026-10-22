#!/usr/bin/env bash
set -euo pipefail

# Starts the local API mock (GET /health -> 200). Optional first argument: port (default 8089).
# Then run the API smoke against it, e.g.:
#   QAFF_API_BASE_URL=http://127.0.0.1:8089 bash scripts/run-api-smoke.sh
exec java scripts/ApiMockServer.java "${1:-8089}"
