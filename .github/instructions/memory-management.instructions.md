---
applyTo: "**/*.java"
description: "Java memory usage rules for bounded data, streams, caches, resource lifecycle, and large payloads."
---

# Purpose

Prevent avoidable memory pressure in Spring Boot services while keeping code simple.

# Rules

- Avoid loading unbounded database results, files, or API payloads into memory.
- Use pagination, streaming, chunking, or batching for large data sets when the project supports it.
- Close resources with try-with-resources or framework-managed lifecycle.
- Avoid static mutable caches unless the target repo already has a cache strategy.
- Keep DTOs focused; do not attach large graphs to responses accidentally.
- Be careful when converting streams to lists in hot paths or large exports.
- Avoid retaining request objects, entities, or large collections beyond their needed scope.

# Good examples

```java
Page<OrderSummary> page = orderRepository.findByStatus(OrderStatus.PAID, pageable);
```

# Bad examples

```java
// Bad: loads every order into memory before filtering.
List<Order> paidOrders = orderRepository.findAll().stream()
        .filter(order -> order.status() == OrderStatus.PAID)
        .toList();
```

# Good Copilot behavior

- Use bounded queries by default.
- Inspect existing export/import or batch patterns before adding large-data logic.
- Prefer project cache abstractions over ad hoc static maps.

# Bad Copilot behavior

- Reading entire files with `readAllBytes()` for large or user-provided files.
- Returning entity graphs that serialize large lazy relationships.
- Adding never-expiring caches for convenience.

# Checklist

- Data volume is bounded or streamed.
- Resources are closed.
- Response graphs are intentional.
- Caches have an owner, limits, and invalidation strategy if used.

# Few-shot mini examples

1. Good: page export rows in chunks instead of loading all orders.
2. Good: use try-with-resources for file streams.
3. Good: return summary DTOs instead of full aggregate graphs.
4. Good: configure cache TTL and max size through existing cache settings.
5. Good: stream large downloads only when the framework and client contract support it.
6. Bad: call `readAllBytes()` for user-provided large files.
7. Bad: call `repository.findAll()` for an unbounded report.
8. Bad: add a static never-expiring map for lookup convenience.
9. Bad: serialize a JPA entity graph with large lazy relationships.
10. Bad: collect a huge stream with `toList()` before filtering.
