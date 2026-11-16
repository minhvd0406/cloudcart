---
applyTo: "**/*.java"
description: "Spring Boot multi-stack rules for MVC, WebFlux, virtual threads, JPA, MongoDB, Cosmos DB, messaging, cache, and scheduled jobs."
---

# Purpose

Help Copilot choose guidance that fits the target repository stack instead of assuming one Spring Boot architecture.

# Rules

- Inspect dependencies and existing code before assuming MVC, WebFlux, JPA, MongoDB, Cosmos DB, messaging, Redis, or Azure.
- Do not mix blocking and reactive APIs accidentally.
- Keep transport DTOs separate from persistence models across JPA, MongoDB, Cosmos DB, and external SDK models.
- For scheduled jobs and message handlers, design idempotency and retry behavior deliberately.
- For cache/Redis, define keys, TTLs, invalidation, and serialization intentionally.
- For virtual threads, verify driver and SDK compatibility.
- Follow existing conventions for commands, tests, mapping, config, and observability.

# Good examples

```java
@Service
class OrderSummaryService {
    private final OrderReadRepository repository;

    Mono<OrderSummaryResponse> getSummary(String orderId) {
        return repository.findSummaryById(orderId)
                .switchIfEmpty(Mono.error(new OrderNotFoundException(orderId)));
    }
}
```

# Bad examples

```java
// Bad: blocks inside reactive service logic.
OrderSummaryResponse getSummary(String orderId) {
    return reactiveRepository.findSummaryById(orderId).block();
}
```

# Good Copilot behavior

- Identify stack from build files and nearby code before editing.
- Use stack-specific tests such as MockMvc, WebTestClient, StepVerifier, DataJpaTest, or Mongo-focused tests.
- Stop and ask when the stack boundary is unclear.

# Bad Copilot behavior

- Assuming every project uses servlet MVC and JPA.
- Returning persistence documents or entities from REST APIs.
- Adding Redis, Azure, or reactive libraries without project evidence.

# Checklist

- Target stack was inspected.
- API and persistence models stay separate.
- Blocking/reactive boundaries are respected.
- Messaging/scheduled/cache behavior is idempotent and observable when relevant.

# Few-shot mini examples

1. Good: inspect dependencies before assuming MVC or WebFlux.
2. Good: use MockMvc for servlet controllers and WebTestClient for reactive controllers.
3. Good: keep JPA entities, Mongo documents, and API DTOs separate.
4. Good: make message handlers idempotent before enabling retries.
5. Good: define cache key, TTL, and invalidation before adding cache behavior.
6. Bad: add WebFlux types to a servlet service without project precedent.
7. Bad: call `.block()` inside reactive service logic.
8. Bad: add Redis, Azure, or Testcontainers dependencies without evidence or request.
9. Bad: assume virtual threads make all blocking libraries safe.
10. Bad: apply JPA migration rules to Cosmos document schema changes.
