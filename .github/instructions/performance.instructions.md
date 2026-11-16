---
applyTo: "**/*.{java,kt,sql}"
description: "Performance rules for bounded queries, N+1 avoidance, indexing, algorithms, allocation, and pragmatic optimization."
---

# Purpose

Improve performance where it matters while avoiding speculative complexity.

# Rules

- Avoid N+1 queries.
- Avoid unbounded database reads and public list endpoints without pagination.
- Prefer database filtering and sorting over loading all rows then filtering in memory.
- Consider indexes for common filters, joins, uniqueness checks, and ordering.
- Keep algorithms simple and proportional to expected input size.
- Avoid unnecessary object allocation in hot paths when it is clearly relevant.
- Use streams only when readable and not hiding expensive operations.
- Do not add caching, async processing, or new infrastructure before measuring or identifying a clear bottleneck.
- Keep performance changes covered by tests or explain how they were verified.

# Good examples

```java
Page<OrderSummary> search(Long customerId, OrderStatus status, Pageable pageable) {
    return orderRepository.findSummaryByCustomerIdAndStatus(customerId, status, pageable);
}
```

```sql
create index idx_orders_customer_status on orders (customer_id, status);
```

# Bad examples

```java
// Bad: loads all rows and filters in memory.
List<Order> paidOrders(Long customerId) {
    return orderRepository.findAll().stream()
            .filter(order -> order.customerId().equals(customerId))
            .filter(order -> order.status() == OrderStatus.PAID)
            .toList();
}
```

```java
// Bad: adds cache before proving the query is slow or repeated.
@Cacheable("orders")
OrderResponse getOrder(Long id) {
    return findOrder(id);
}
```

# Good Copilot behavior

- Inspect query patterns, entity graphs, indexes, and existing performance tests before changing code.
- Prefer simple bounded queries before caches or async designs.
- Call out performance assumptions when measurement is unavailable.

# Bad Copilot behavior

- Recommending new infrastructure for a local inefficiency.
- Ignoring pagination on endpoints that can grow.
- Hiding expensive remote or database calls inside streams.

# Checklist

- Data access is bounded.
- Required indexes or migrations were considered.
- N+1 risk is handled.
- Algorithm complexity is appropriate.
- Optimization is justified by evidence or clear risk.

# Few-shot mini examples

1. Good: use a repository query for `customerId` and `status` instead of filtering in Java.
2. Good: use `@EntityGraph` or projection to prevent N+1 on order detail.
3. Good: add an index for a new high-volume search filter.
4. Good: prefer `Slice` when a count query is unnecessary.
5. Good: mention performance assumptions when measurement is unavailable.
6. Bad: add caching before proving repeated slow reads.
7. Bad: make relationships `EAGER` globally to fix one endpoint.
8. Bad: hide database calls inside a stream mapping function.
9. Bad: return unbounded lists from public endpoints.
10. Bad: add async processing to mask a simple inefficient query.
