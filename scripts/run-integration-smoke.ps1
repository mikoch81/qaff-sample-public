Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

Write-Host "No integration smoke modules are present in this project (Quality Impact Pack is not enabled)."
Write-Host "Enable packs.qualityImpact in your qaff config and re-generate to add integration smoke tests."
exit 0
