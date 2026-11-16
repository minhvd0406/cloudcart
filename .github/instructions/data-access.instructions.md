---
applyTo: "**/*{Repository,Dao,Entity,Projection,Specification,Query,Jpa,Jdbc,Migration}.java,**/*.{sql}"
description: "Spring Data JPA, repository, query, entity, migration, pagination, Slice/Page, and N+1 avoidance rules."
---

# Purpose

Keep persistence code safe, bounded, testable, and aligned with Spring Data JPA practices.

# Rules

- Keep persistence access in repositories or data-access adapters.
- Use Spring Data query methods for simple lookups.
- Use explicit `@Query`, specifications, projections, entity graphs, or fetch joins when query method names become unclear or inefficient.
- Use pagination for queries backing list endpoints.
- Use `Slice` instead of `Page` when the caller only needs next-page detection and a count query is expensive.
- Avoid N+1 queries with `@EntityGraph`, fetch joins, projections, batch settings, or purpose-built read models.
- Do not expose JPA entities through REST APIs.
- Avoid lazy loading during JSON serialization.
- Prefer lazy relationships by default; fetch what the use case needs.
- Use `Optional<T>` for repository lookups where absence is expected.
- Use `existsBy...` for existence checks.
- Consider indexes or migrations for new query patterns.
- Use batch operations when many rows are updated or inserted.
- Use optimistic locking when concurrent updates to the same row can conflict.
- Test custom queries with `@DataJpaTest` or the repository test style used by the target project.

# Good examples

```java
interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status, Pageable pageable);
}
```

```java
interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "lines")
    Optional<Order> findWithLinesById(Long id);
}
```

```java
interface OrderRepository extends JpaRepository<Order, Long> {
    Slice<OrderSummary> findByStatusOrderByCreatedAtDesc(OrderStatus status, Pageable pageable);
}
```

# Bad examples

```java
// Bad: unbounded and usually unsafe behind public APIs.
List<Order> findAllByCustomerId(Long customerId);
```

```java
// Bad: lazy loading may happen during JSON serialization.
@GetMapping("/orders/{id}")
Order getOrder(@PathVariable Long id) {
    return orderRepository.findById(id).orElseThrow();
}
```

# Good Copilot behavior

- Inspect entity mappings, existing indexes, repository tests, and service transactions before changing queries.
- Flush and clear in tests when proving database behavior rather than persistence-context caching.
- Prefer bounded reads and query-specific DTOs for read-heavy endpoints.

# Bad Copilot behavior

- Calling `findAll()` then filtering in memory.
- Adding broad `EAGER` relationships to fix one endpoint.
- Building JPQL or SQL through untrusted string concatenation.

# Checklist

- Query is bounded or paginated.
- Required associations are fetched deliberately.
- REST layer does not return entities.
- New query patterns consider indexes.
- Custom query behavior has focused tests.

# Few-shot mini examples

1. Good: use `Page<OrderSummary>` for a customer order search endpoint.
2. Good: use `Slice` when only next-page detection is needed.
3. Good: use `@EntityGraph(attributePaths = "lines")` for an order detail use case.
4. Good: add an index for new common filters such as `(customer_id, status)`.
5. Good: flush and clear in repository tests before verifying query behavior.
6. Bad: call `findAll()` then filter by customer in Java.
7. Bad: mark a large relationship `EAGER` to fix one endpoint.
8. Bad: return a JPA entity from a REST controller.
9. Bad: concatenate user input into JPQL or SQL.
10. Bad: add a custom query without testing empty result and pagination behavior.
