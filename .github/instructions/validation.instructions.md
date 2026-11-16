---
applyTo: "**/*{Controller,Request,Response,Dto,DTO,Service,Properties,Config,Configuration}.java"
description: "Validation rules for request DTOs, path/query parameters, configuration properties, and business invariants."
---

# Purpose

Keep validation close to the correct boundary while preserving business invariants in service or domain code.

# Rules

- Use Bean Validation on request DTOs.
- Validate path variables and query parameters when constraints matter.
- Validate `@ConfigurationProperties` when invalid config should fail startup.
- Keep business invariants in the service or domain layer, not only in controllers or frontend code.
- Do not trust client-managed fields such as id, owner, status, role, audit fields, or totals.
- Avoid duplicating validation rules inconsistently across controllers.
- Use clear domain exceptions for invalid state transitions.
- Keep validation messages safe and useful.

# Good examples

```java
record CreateOrderRequest(
        @NotNull Long customerId,
        @NotEmpty List<@Valid OrderLineRequest> lines) {
}
```

```java
void cancel(Order order) {
    if (order.status() == OrderStatus.SHIPPED) {
        throw new InvalidOrderStateException("Shipped orders cannot be cancelled");
    }
    order.cancel();
}
```

# Bad examples

```java
// Bad: raw maps hide the API contract and push validation into controller code.
@PostMapping("/orders")
OrderResponse create(@RequestBody Map<String, Object> body) {
    return orderService.create(body);
}
```

```java
// Bad: only frontend validation protects an important business invariant.
void cancel(Order order) {
    order.setStatus(OrderStatus.CANCELLED);
}
```

# Good Copilot behavior

- Inspect existing DTO validation, exception handling, and business rule placement first.
- Add validation tests for request boundaries and unit tests for domain invariants.
- Keep server-managed fields out of create/update request DTOs.

# Bad Copilot behavior

- Validating only in JavaScript or controller conditionals.
- Accepting arbitrary maps for stable API contracts.
- Letting request DTOs decide ownership or state transitions.

# Checklist

- Boundary inputs are validated.
- Business invariants are enforced server-side.
- Config validation fails fast when needed.
- Server-managed fields are protected.
- Validation failures have tests.

# Few-shot mini examples

1. Good: use `@Valid @RequestBody CreateOrderRequest`.
2. Good: validate nested list items with `List<@Valid OrderLineRequest>`.
3. Good: reject invalid state transitions in domain/service code.
4. Good: validate `@ConfigurationProperties` required values.
5. Good: add a test that invalid request returns `400` with field details.
6. Bad: trust client-provided `status`, `ownerId`, or `createdAt`.
7. Bad: rely on frontend validation only.
8. Bad: allow blank strings for required business identifiers.
9. Bad: skip path/query validation for constrained values.
10. Bad: map every validation error to a generic `500`.
