Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

# Starts the local API mock (GET /health -> 200). Optional first argument: port (default 8089).
# Then run the API smoke against it, e.g.:
#   $env:QAFF_API_BASE_URL = "http://127.0.0.1:8089"; .\scripts\run-api-smoke.ps1
$port = if ($args.Count -gt 0) { $args[0] } else { "8089" }
java scripts/ApiMockServer.java $port
