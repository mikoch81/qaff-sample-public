$ErrorActionPreference = 'Stop'

Write-Host '=== Running iOS native smoke ==='

mvn -B -pl apps/demo-shop/ios -am verify "-Dappium.enabled=true"

Write-Host ''
Write-Host '=== iOS native smoke completed ==='
