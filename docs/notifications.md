# Run notifications (opt-in)

Generated because `integrations.notifications` enables Slack and at least
one `ci.<provider>.notify` is on. At the end of each such pipeline one step runs
`qaff notify-run --mode live` per provider and posts **one** message: project,
blueprint, apps, run status, a link to the run and — when
`framework-performance/reports/quality-impact-summary.json` (and
`trend-report-summary.json`) exist — the Quality Impact verdicts. Nothing else is
created remotely, and a failed notification never changes the build result.

## Secrets

- `SLACK_WEBHOOK_URL` — the Slack incoming webhook URL.

The URL is read only from the CI environment. It is never written to the qaff
config, `qa-factory.lock.json`, generated files or the request file.

## Per CI provider

- **GitHub Actions** — repository secret; last step with `if: always()`, status from `job.status`.

## Local preview

```bash
qaff notify-run --project . --provider slack --status success
```

Without `--mode live` nothing is sent: the payload is written to
`.qaff/notifications/<provider>.request.json` (use `--force` to overwrite).
