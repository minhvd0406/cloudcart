---
applyTo: "**/*.java"
description: "Java collection usage rules for null safety, immutability, iteration, defensive copies, and readable transformations."
---

# Purpose

Use Java collections in ways that are clear, safe, and efficient enough for typical Spring Boot services.

# Rules

- Return empty collections instead of `null`.
- Prefer immutable collections for DTOs, constants, and values that should not be mutated by callers.
- Make defensive copies when storing caller-provided mutable collections.
- Choose the collection type that matches the behavior: `List` for ordered values, `Set` for uniqueness, `Map` for keyed lookup.
- Avoid modifying a collection while iterating unless using a safe iterator method.
- Avoid collecting huge result sets in memory when pagination or streaming is needed.
- Keep transformations readable; a loop is fine when stream logic becomes hard to follow.

# Good examples

```java
record OrderResponse(Long id, List<OrderLineResponse> lines) {
    OrderResponse {
        lines = List.copyOf(lines);
    }
}
```

# Bad examples

```java
// Bad: callers must handle null and may get runtime failures.
List<OrderLine> linesFor(Order order) {
    return order.hasLines() ? order.lines() : null;
}
```

# Good Copilot behavior

- Preserve existing collection style and nullability conventions.
- Use immutable copies for API responses and value objects.
- Use pagination for collections that can grow.

# Bad Copilot behavior

- Returning mutable internal collections from entities or services.
- Replacing simple loops with unreadable stream pipelines.
- Loading all database rows just to build a collection in memory.

# Checklist

- No new `null` collection returns.
- Mutability is intentional.
- Collection type matches access pattern.
- Large collections are paginated or bounded.

# Few-shot mini examples

1. Good: return `List.of()` when an order has no lines.
2. Good: use `List.copyOf(lines)` in immutable response DTO constructors.
3. Good: use `Set<OrderTag>` when duplicate tags must be prevented.
4. Good: use `Map<OrderType, OrderProcessor>` for stable keyed lookup.
5. Good: page repository results before mapping them to DTOs.
6. Bad: return `null` for an empty collection.
7. Bad: expose an entity's internal mutable `List`.
8. Bad: call `findAll()` then build a large collection in memory.
9. Bad: modify a `List` while iterating with an enhanced for-loop.
10. Bad: use a complex stream pipeline where a loop would show branching clearly.
