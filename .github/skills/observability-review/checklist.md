# Observability Review Checklist

- Inspect existing logging, metrics, tracing, actuator, dashboard, and alert conventions.
- Confirm logs include operation, outcome, and safe identifiers.
- Confirm logs avoid secrets, tokens, auth headers, PII, and full payloads.
- Confirm metrics use stable names and low-cardinality tags.
- Confirm tracing/correlation is propagated across async, messaging, and client boundaries when relevant.
- Confirm actuator endpoints and health details are exposed intentionally.
- Confirm retries, DLQ, scheduled jobs, and external calls have useful safe diagnostics.
- Confirm observability changes do not create excessive cost or noise.
- Add tests for metrics/logging only where the project supports it and behavior depends on it.
