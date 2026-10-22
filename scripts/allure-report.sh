#!/usr/bin/env bash
# Builds one Allure HTML report for the whole reactor. Run after mvn verify.
# Output: target/site/allure-maven-plugin/index.html
set -euo pipefail
cd "$(dirname "$0")/.."

rm -rf target/allure-results
mkdir -p target/allure-results
found=0
while IFS= read -r dir; do
  cp -r "$dir"/. target/allure-results/
  found=1
done < <(find . -path ./target -prune -o -type d -path '*/target/allure-results' -print)
if [ "$found" -eq 0 ]; then
  echo "No allure-results found - run mvn verify first." >&2
  exit 1
fi

mvn -B -ntp -N allure:report
echo "Allure report: target/site/allure-maven-plugin/index.html"
