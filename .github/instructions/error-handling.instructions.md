---
applyTo: "**/*{Exception,Error,Advice,Handler,Controller,Service}.java"
description: "Spring Boot exception, ProblemDetail, validation error, and API error mapping rules."
---

# Purpose

Make failures explicit, user-safe, and mapped to the correct HTTP or application response.

# Rules

- Use domain-specific exceptions for expected failures.
- Map exceptions centrally with `@ControllerAdvice` when handling REST API errors.
- Use `ProblemDetail` or the target repository's existing error contract.
- Do not leak stack traces, SQL, credentials, tokens, internal hostnames, or provider payloads in API errors.
- Map missing resources to `404`.
- Map invalid state or conflict with current resource state to `409`.
- Map validation failures to `400` with useful field details when the project supports it.
- Avoid catch-all handlers that swallow important exceptions or convert every failure to `500`.
- Let unexpected failures surface to the framework after safe logging or centralized handling.

# Good examples

```java
class OrderNotFoundException extends RuntimeException {
    OrderNotFoundException(Long orderId) {
        super("Order not found: " + orderId);
    }
}
```

```java
@ExceptionHandler(OrderNotFoundException.class)
ProblemDetail handleOrderNotFound(OrderNotFoundException exception) {
    ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    detail.setTitle("Order not found");
    return detail;
}
```

# Bad examples

```java
// Bad: service returns null and forces the controller to guess what happened.
Order findOrder(Long id) {
    return orderRepository.findById(id).orElse(null);
}
```

```java
// Bad: all failures become 500 and details may leak to clients.
@ExceptionHandler(Exception.class)
String handle(Exception exception) {
    return exception.toString();
}
```

# Good Copilot behavior

- Inspect existing exception classes and global handlers before adding a new pattern.
- Add tests for not-found, validation, and conflict responses when API behavior changes.
- Keep messages useful but safe.

# Bad Copilot behavior

- Catching exceptions only to return `null`, `false`, or an empty list.
- Creating duplicate error contracts.
- Logging sensitive request data during error handling.

# Checklist

- Expected failure has a specific exception or result type.
- API status code is precise.
- Error body follows project convention.
- Internals and secrets are not exposed.
- Negative-path tests cover the mapping.

# Few-shot mini examples

1. Good: throw `OrderNotFoundException` and map it to `404`.
2. Good: map invalid state such as shipped cancellation to `409`.
3. Good: return field-level validation details when the existing contract supports them.
4. Good: log unexpected failures safely, then let central handling produce a safe response.
5. Good: test not-found, validation, and conflict response bodies.
6. Bad: return `null` when a repository lookup is missing.
7. Bad: catch every exception and return `200` with `"failed"`.
8. Bad: expose SQL, stack traces, tokens, or provider payloads in API errors.
9. Bad: create a second error shape beside an existing `ProblemDetail` contract.
10. Bad: convert all failures to `500` because mapping is faster to write.
