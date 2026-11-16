---
applyTo: "**/{controller,controllers,web,api,resource,resources}/**/*.java,**/*{Controller,Resource,Endpoint,Request,Response}.java"
description: "Spring REST API design rules for DTOs, validation, status codes, pagination, versioning, and error contracts."
---

# Purpose

Keep REST APIs stable, explicit, validated, and safe across Spring Boot services.

# Rules

- Use request and response DTOs instead of JPA entities.
- Validate request bodies with `@Valid`.
- Validate path variables and query parameters when constraints matter.
- Use HTTP status codes that match behavior: `201` for creates, `204` for empty deletes, `400` for validation, `404` for missing resources, `409` for state conflicts.
- Use pagination for collection endpoints that can grow.
- Return the project's existing page response shape when one exists.
- Use a consistent error response such as `ProblemDetail` or the repository's established contract.
- Version APIs according to the target repo convention; do not invent versioning if none exists.
- Consider idempotency for create, retry, payment, messaging, or cancellation operations.
- Do not allow create requests to set server-managed fields such as id, status, owner, audit timestamps, or roles unless explicitly required.

# Good examples

```java
@PostMapping("/api/v1/orders")
@ResponseStatus(HttpStatus.CREATED)
OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
    return orderService.createOrder(request);
}

record CreateOrderRequest(
        @NotNull Long customerId,
        @NotEmpty List<@Valid OrderLineRequest> lines) {
}
```

```java
@GetMapping("/api/v1/orders")
Page<OrderSummaryResponse> searchOrders(
        @RequestParam Long customerId,
        @RequestParam OrderStatus status,
        Pageable pageable) {
    return orderService.search(customerId, status, pageable);
}
```

# Bad examples

```java
// Bad: exposes a persistence entity and accepts server-managed fields.
@PostMapping("/api/v1/orders")
Order create(@RequestBody Order order) {
    return orderRepository.save(order);
}
```

```java
// Bad: unbounded list endpoint can become a production incident.
@GetMapping("/api/v1/orders")
List<OrderResponse> allOrders() {
    return orderService.findAll();
}
```

# Good Copilot behavior

- Inspect existing URL, versioning, DTO, validation, and error response conventions first.
- Add negative tests for validation and not-found/conflict behavior.
- Keep public contracts stable unless the user requests a breaking change.

# Bad Copilot behavior

- Returning entities because it is faster to write.
- Adding endpoints without validation or tests.
- Creating a new error shape beside an existing one.

# Checklist

- DTOs protect the API boundary.
- Request, path, and query inputs are validated.
- Status codes match behavior.
- Collection endpoints are paginated or bounded.
- Error responses follow project convention.

# Few-shot mini examples

1. Good: create endpoint returns `201` and a response DTO.
2. Good: update endpoint rejects server-managed fields from request DTOs.
3. Good: list endpoint accepts `Pageable` or an existing page request type.
4. Good: validation failure returns the existing error shape with field details.
5. Good: protected resource endpoint has `401`, `403`, and ownership tests.
6. Bad: accept `Order` entity in `@RequestBody`.
7. Bad: return `List<OrderResponse>` for an unbounded search endpoint.
8. Bad: use `200` for a successful delete with no body when project uses `204`.
9. Bad: invent `/v2` routes when the repo has no versioning convention.
10. Bad: add endpoint behavior without controller tests.
