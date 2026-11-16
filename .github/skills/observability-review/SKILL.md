---
name: observability-review
description: Use when reviewing Spring Boot logs, metrics, tracing, actuator exposure, correlation IDs, dashboards, alerts, cardinality, and sensitive-data-safe diagnostics.
---

# Skill

Observability review playbook for Spring Boot services.

# When to use

- Reviewing logging, metrics, tracing, actuator, health, dashboard, or alert changes.
- Adding diagnostics to controllers, services, clients, jobs, or message handlers.
- Checking sensitive data in logs and metric labels.
- Reviewing performance or reliability changes that need visibility.

# When not to use

- Pure business logic changes with no operational signal impact.
- Security-only reviews, though sensitive logging still matters.
- Creating production dashboards without repository or platform evidence.

# Required context to inspect

- Existing logging style, metric registry usage, tracing/correlation patterns, actuator config, dashboards/docs, alert docs, and tests.
- Controllers, clients, jobs, message handlers, and exception handlers touched by the change.
- Security and privacy rules for sensitive fields.

# Step-by-step workflow

1. Identify the operation, outcome, latency, error, and business event that need visibility.
2. Check logs are structured, actionable, and safe.
3. Check metrics have stable low-cardinality names and tags.
4. Check trace/correlation context propagates through async, messaging, and client calls.
5. Check actuator endpoints and health details are exposed safely.
6. Check alert/dashboard notes when SLO-impacting behavior changes.
7. Check tests or documentation for important observability behavior when practical.

# Java best practices

- Use structured logging placeholders instead of string concatenation.
- Avoid logging entire objects or payloads.
- Keep metric names stable and generic.
- Avoid expensive diagnostic computation on every request unless guarded.

# Spring Boot best practices

- Use existing logging, Micrometer, tracing, and actuator conventions.
- Use safe identifiers such as order ID, message ID, operation, status, and reason.
- Avoid high-cardinality tags such as user ID, email, raw URL, token, exception message, or full query.
- Do not expose sensitive actuator details without project precedent.

# Multi-stack adaptation notes

- MVC/WebFlux: propagate correlation IDs and include operation/outcome in logs.
- Messaging: log message ID, event type, attempt, outcome, and DLQ/retry decisions safely.
- JPA/data: add query/count visibility only when useful and safe.
- Azure/cloud clients: log resource logical names, not connection strings or tokens.

# Thread-safety rules

- Do not store correlation ID or request context in singleton fields.
- Use MDC or framework context according to existing conventions.
- Be careful with async execution where MDC/security context may not propagate automatically.

# Configuration-driven design rules

- Make log levels, sampling, actuator exposure, and client timeouts follow existing configuration conventions.
- Keep metric toggles and endpoint exposure safe by default.
- Do not hardcode dashboard URLs or platform-specific identifiers in reusable kit examples.

# Testing requirements

- Prefer behavior tests for code paths; logging assertions only when logs are part of the contract or regression.
- Test metric increments/tags when business metrics are important and project style supports it.
- Verify sensitive fields are not returned or logged when relevant.

# Few-shot examples

1. Good: safe structured log in `examples/practical-patterns.good.md`.
2. Good: low-cardinality metric tags in `examples/practical-patterns.good.md`.
3. Good: message handler logs retry outcome in `examples/practical-patterns.good.md`.
4. Good: actuator exposure is narrow in `examples/practical-patterns.good.md`.
5. Good: correlation ID passed to client call in `examples/practical-patterns.good.md`.
6. Bad: logs full request payload in `examples/review-traps.bad.md`.
7. Bad: metric tag uses user email in `examples/review-traps.bad.md`.
8. Bad: string-concatenated expensive log in `examples/review-traps.bad.md`.
9. Bad: exposes all actuator details in `examples/review-traps.bad.md`.
10. Bad: async task loses correlation context in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- Logs are structured, useful, and safe.
- Metrics use stable names and low-cardinality tags.
- Tracing/correlation survives relevant boundaries.
- Actuator exposure is deliberate.
- Sensitive data is not logged or tagged.
- Operational docs/tests are updated when signal changes.

# Final response expectations

- Findings ordered by severity.
- Sensitive-data and cardinality risks.
- Suggested logs/metrics/tests.
- Open assumptions about platform conventions.
