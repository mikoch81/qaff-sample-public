Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"
# Builds one Allure HTML report for the whole reactor. Run after mvn verify.
# Output: target/site/allure-maven-plugin/index.html
Set-Location (Join-Path $PSScriptRoot "..")

$target = "target/allure-results"
if (Test-Path $target) { Remove-Item -Recurse -Force $target }
New-Item -ItemType Directory -Force $target | Out-Null
$rootResults = (Resolve-Path $target).Path
$dirs = @(Get-ChildItem -Recurse -Directory -Filter allure-results |
  Where-Object { $_.Parent.Name -eq "target" -and $_.FullName -ne $rootResults })
if ($dirs.Count -eq 0) { throw "No allure-results found - run mvn verify first." }
foreach ($d in $dirs) { Copy-Item -Recurse -Force (Join-Path $d.FullName "*") $target }

mvn -B -ntp -N allure:report
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Write-Host "Allure report: target/site/allure-maven-plugin/index.html"
