Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

Write-Host "=== Running API smoke tests ==="

# Optional runtime overrides forwarded as -D system properties.
# Set QAFF_API_BASE_URL or QAFF_API_TIMEOUT_SECONDS in your shell to
# point API smoke at a real environment instead of the offline stub.
$mavenOverrides = @()
if ($env:QAFF_API_BASE_URL) {
    $mavenOverrides += "-Dapi.baseUrl=$($env:QAFF_API_BASE_URL)"
}
if ($env:QAFF_API_TIMEOUT_SECONDS) {
    $mavenOverrides += "-Dapi.timeoutSeconds=$($env:QAFF_API_TIMEOUT_SECONDS)"
}
$MAVEN_OVERRIDES = ($mavenOverrides -join " ")

mvn -B -pl apps/demo-shop/api -am verify $MAVEN_OVERRIDES

Write-Host ""
Write-Host "=== API smoke tests completed ==="
