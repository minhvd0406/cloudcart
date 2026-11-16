---
applyTo: "**/*{Controller,Service,Client,Handler,Router,Configuration}.java"
description: "Rules for servlet MVC, WebFlux, reactive composition, virtual threads, blocking boundaries, and reactive tests."
---

# Purpose

Prevent accidental mixing of servlet, reactive, and virtual-thread execution models.

# Rules

- In WebFlux service logic, do not call `.block()`.
- Do not call `.subscribe()` manually in business services unless the code is an explicit fire-and-forget boundary with documented error handling.
- Use reactive composition with `map`, `flatMap`, `zip`, `switchIfEmpty`, and `onErrorMap` where appropriate.
- Use WebTestClient and StepVerifier for reactive tests.
- In servlet apps, configure timeouts for remote calls and avoid long blocking chains without reason.
- For virtual threads, inspect target library, JDBC driver, SDK, and framework support before changing execution assumptions.
- Keep DTO and error handling conventions consistent across MVC and WebFlux.

# Good examples

```java
Mono<OrderResponse> cancelOrder(String orderId) {
    return orderRepository.findById(orderId)
            .switchIfEmpty(Mono.error(new OrderNotFoundException(orderId)))
            .flatMap(order -> order.cancel()
                    .then(orderRepository.save(order)))
            .map(OrderResponse::from);
}
```

# Bad examples

```java
// Bad: subscribe hides errors from the caller and bypasses normal control flow.
void cancelOrder(String orderId) {
    orderRepository.findById(orderId)
            .flatMap(orderRepository::save)
            .subscribe();
}
```

# Good Copilot behavior

- Inspect whether controllers return regular DTOs, `Mono`, `Flux`, `ResponseEntity`, or server-sent events.
- Preserve the target app's execution model.
- Add reactive tests for reactive behavior and MVC tests for servlet behavior.

# Bad Copilot behavior

- Blocking inside reactive services.
- Returning `Mono` from one layer and blocking in the next.
- Assuming virtual threads remove the need for timeouts, idempotency, or thread safety.

# Checklist

- No accidental `.block()` or hidden `.subscribe()`.
- Remote calls have timeouts in servlet flows.
- Reactive tests use StepVerifier/WebTestClient.
- Virtual-thread assumptions are documented and supported.

# Few-shot mini examples

1. Good: compose `Mono` with `switchIfEmpty` for not-found behavior.
2. Good: test reactive service errors with StepVerifier.
3. Good: use WebTestClient for WebFlux controllers.
4. Good: keep servlet flows blocking but time-bounded with configured client timeouts.
5. Good: verify driver and SDK compatibility before enabling virtual threads.
6. Bad: call `.block()` in a WebFlux service method.
7. Bad: call `.subscribe()` to hide errors from the caller.
8. Bad: return `Mono` from one layer and immediately block in the next.
9. Bad: mix JPA blocking calls into reactive chains without a deliberate boundary.
10. Bad: assume virtual threads remove the need for idempotency or thread safety.
