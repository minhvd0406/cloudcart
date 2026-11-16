---
applyTo: "**/*{Controller,Service,Handler,Listener,Consumer,Producer,Scheduler,Job,Config,Configuration}.java,**/application*.{yml,yaml,properties}"
description: "Structured logging, safe context, correlation IDs, metrics, tracing, and low-noise observability rules."
---

# Purpose

Make production behavior understandable without leaking sensitive data or creating noisy telemetry.

# Rules

- Add useful structured logs at important state changes, failures, and external boundaries.
- Include safe identifiers such as order id, status, operation, and outcome.
- Do not log secrets, tokens, passwords, authorization headers, cookies, full request bodies, or PII.
- Use correlation or trace IDs if the project already provides them.
- Add metrics around important business operations, failures, latency, and external calls when the project has a metrics pattern.
- Avoid metric labels with unbounded cardinality, such as user id, order id, email, or raw exception messages.
- Log enough context for diagnosis without exposing internals to clients.
- Avoid log spam in loops and hot paths.

# Good examples

```java
log.info("order_cancelled orderId={} status={} outcome={}", orderId, OrderStatus.CANCELLED, "success");
```

```java
counter("orders.cancellation.failed", "reason", "invalid_state").increment();
```

# Bad examples

```java
// Bad: full payload may include PII and secrets.
log.info("create order request={}", request);
```

```java
// Bad: orderId as a metric label creates unbounded cardinality.
counter("orders.cancelled", "orderId", orderId.toString()).increment();
```

# Good Copilot behavior

- Inspect existing logging, metrics, tracing, and naming conventions.
- Add telemetry only where it helps operations or debugging.
- Keep error logs safe and actionable.

# Bad Copilot behavior

- Adding noisy logs to every method entry and exit.
- Creating new metric naming conventions beside existing ones.
- Logging stack traces for expected validation failures.

# Checklist

- Logs include safe, useful context.
- Secrets and PII are excluded.
- Metrics avoid unbounded labels.
- Telemetry follows existing project conventions.
- Expected failures are not logged as noisy production errors.

# Few-shot mini examples

1. Good: log `orderId`, operation, and outcome with placeholders.
2. Good: tag metrics with low-cardinality values like `operation` and `outcome`.
3. Good: propagate correlation ID into outbound client headers when the repo does that.
4. Good: log retry exhaustion once with safe context.
5. Good: expose actuator endpoints only according to existing ops conventions.
6. Bad: log full request/response bodies by default.
7. Bad: tag metrics with email, token, raw URL, or exception message.
8. Bad: use string concatenation that serializes expensive objects before log-level checks.
9. Bad: expose every actuator endpoint with details in a reusable default.
10. Bad: log expected validation errors as production error noise.
