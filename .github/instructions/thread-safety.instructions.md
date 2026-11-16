---
applyTo: "**/*.java"
description: "Thread-safety rules for Spring singleton beans, request state, shared mutable data, virtual threads, and async work."
---

# Purpose

Prevent unsafe shared state in Spring applications across servlet threads, reactive pipelines, scheduled jobs, message listeners, and virtual-thread execution.

# Rules

- Treat Spring singleton beans as shared across requests and threads.
- Do not store request, user, tenant, correlation, or payload state in fields.
- Keep dependencies `private final` and immutable where possible.
- Avoid static mutable state unless it is thread-safe, bounded, and owned deliberately.
- Use local variables for per-request calculations.
- Use concurrent collections or synchronization only when shared mutable state is necessary and justified.
- For virtual threads, inspect driver/client compatibility before assuming blocking calls are safe.
- Do not assume `ThreadLocal` context automatically works across async/reactive/virtual-thread boundaries.

# Good examples

```java
@Service
class OrderPricingService {
    BigDecimal total(OrderRequest request) {
        return request.lines().stream()
                .map(OrderLineRequest::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
```

# Bad examples

```java
// Bad: singleton bean keeps request-specific state in fields.
@Service
class OrderPricingService {
    private Long currentCustomerId;
    private BigDecimal currentTotal;
}
```

# Good Copilot behavior

- Inspect whether the target code runs in MVC, WebFlux, scheduled jobs, message listeners, or async executors.
- Keep beans stateless by default.
- Add concurrency or duplicate-message tests when shared state or idempotency changes.

# Bad Copilot behavior

- Adding mutable fields to avoid passing method parameters.
- Using `ThreadLocal` for request data without checking framework context propagation.
- Assuming virtual threads fix unsafe shared state.

# Checklist

- Singleton beans do not hold request/user state.
- Shared mutable state is avoided or protected.
- Async/reactive/virtual-thread context assumptions are explicit.
- Tests cover concurrent or repeated execution when relevant.

# Few-shot mini examples

1. Good: keep per-request values in method parameters.
2. Good: make DTOs immutable with records or defensive copies when compatible.
3. Good: use database unique constraints for duplicate command protection.
4. Good: preserve MDC/security context according to project async conventions.
5. Good: add repeated execution tests for idempotent handlers.
6. Bad: store current user in a service field.
7. Bad: use a mutable static `ArrayList` as shared state.
8. Bad: assume `HashMap` is safe because access seems rare.
9. Bad: call blocking code in reactive chains and hope scheduling fixes it.
10. Bad: treat virtual threads as a fix for unsafe shared mutation.
