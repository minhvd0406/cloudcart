---
applyTo: "**/*.java"
description: "Java and Spring concurrency rules for shared state, async work, transactions, locks, and parallel streams."
---

# Purpose

Avoid data races, hidden side effects, and unsafe async behavior in Spring Boot applications.

# Rules

- Avoid mutable shared state in singleton Spring beans.
- Prefer local variables and immutable values.
- Use thread-safe types intentionally when state must be shared.
- Do not use `parallelStream()` for I/O, database, or request-scoped work without strong evidence.
- Keep transaction and security context behavior in mind before adding async execution.
- Use database constraints, optimistic locking, or explicit locks for concurrent state changes when needed.
- Do not start raw threads in Spring components; use project-approved executors or scheduling mechanisms.

# Good examples

```java
@Transactional
void confirmPayment(Long orderId) {
    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    order.markPaid();
}
```

# Bad examples

```java
// Bad: singleton bean stores request-specific mutable state.
@Service
class OrderService {
    private Long currentCustomerId;
}
```

# Good Copilot behavior

- Inspect existing async, scheduling, locking, and transaction patterns before adding concurrency.
- Prefer database-backed consistency for persisted state.
- Add tests for duplicate commands or concurrent updates when relevant.

# Bad Copilot behavior

- Adding `parallelStream()` as a performance shortcut.
- Using static mutable caches without eviction or thread safety.
- Spawning raw threads that bypass Spring lifecycle and context.

# Checklist

- Singleton beans do not hold request state.
- Shared state is immutable or thread-safe.
- Async work uses project conventions.
- Concurrency conflicts are handled deliberately.

# Few-shot mini examples

1. Good: keep `customerId` as a local variable inside a service method.
2. Good: use `@Version` for JPA aggregates that receive concurrent updates.
3. Good: use a project-configured executor for `@Async` work.
4. Good: make retried command handlers idempotent with a request or message key.
5. Good: document transaction/security context assumptions before adding async work.
6. Bad: store the current principal in a singleton service field.
7. Bad: use `parallelStream()` around repository or remote calls.
8. Bad: start `new Thread(...)` inside a Spring bean.
9. Bad: use a mutable static `HashMap` as a cache without synchronization or eviction.
10. Bad: assume virtual threads remove the need for database locking or idempotency.
