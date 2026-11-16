---
applyTo: "**/*{Client,Service,Adapter,Gateway,Producer,Consumer,Listener,Scheduler,Job,Config,Configuration}.java"
description: "Resilience rules for timeouts, retries, idempotency, circuit breakers, messaging recovery, and transient failures."
---

# Purpose

Make integrations and background work reliable without retry storms or hidden data inconsistencies.

# Rules

- Configure timeouts for outbound HTTP, database, messaging, and external client calls according to project conventions.
- Retry only transient failures and always use limits.
- Do not retry validation, authorization, conflict, or other non-transient failures.
- Preserve idempotency for retried commands, callbacks, event consumers, and scheduled jobs.
- Use circuit breakers, rate limiters, or bulkheads only when the target project already uses them or the task asks for them.
- For messaging, consider dead-letter handling, poison messages, and replay behavior when the project has messaging infrastructure.
- Log retry exhaustion with safe context.
- Avoid long-running transactions around remote calls.

# Good examples

```java
boolean shouldRetry(Throwable failure) {
    return failure instanceof SocketTimeoutException
            || failure instanceof TransientDataAccessException;
}
```

# Bad examples

```java
// Bad: retries every failure forever, including bad requests and authorization failures.
while (true) {
    paymentClient.charge(command);
}
```

# Good Copilot behavior

- Inspect existing client configuration and resilience libraries before adding patterns.
- Prefer explicit timeouts and bounded retries.
- Add tests for idempotent retry behavior when the task changes command processing.

# Bad Copilot behavior

- Adding a retry annotation without thinking about duplicate side effects.
- Introducing a circuit breaker dependency without project precedent.
- Retrying `400`, `401`, `403`, or domain conflicts.

# Checklist

- Timeouts are explicit where supported.
- Retries are bounded and selective.
- Side effects are idempotent or protected.
- Messaging failure paths are considered when relevant.
- Remote calls do not accidentally extend database transactions.

# Few-shot mini examples

1. Good: configure connect and read timeouts for outbound clients.
2. Good: retry only transient exceptions with a bounded policy.
3. Good: use an idempotency key for payment or cancellation commands.
4. Good: let poison messages reach DLQ according to broker conventions.
5. Good: perform remote calls outside long database transactions when possible.
6. Bad: retry `400`, `401`, `403`, or domain conflicts.
7. Bad: loop forever around an external payment call.
8. Bad: add `@Retryable` without protecting duplicate side effects.
9. Bad: swallow listener exceptions that should trigger retry/DLQ.
10. Bad: introduce a circuit breaker library without project precedent or request.
