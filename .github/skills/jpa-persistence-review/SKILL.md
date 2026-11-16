---
name: jpa-persistence-review
description: Use when reviewing Spring Data JPA repositories, entities, queries, transactions, migrations, indexes, pagination, projections, locking, and persistence tests.
---

# Skill

JPA persistence review playbook for Spring Boot services.

# When to use

- Reviewing JPA entities, repositories, JPQL/native queries, specifications, projections, and migrations.
- Adding or changing queries behind APIs, jobs, reports, or integrations.
- Checking N+1 risk, transaction boundaries, locking, indexes, and repository tests.

# When not to use

- MongoDB, Cosmos DB, or non-JPA persistence without JPA involvement.
- Pure API contract changes with no persistence behavior.
- Database administration tasks that require production access outside the repository.

# Required context to inspect

- Entities, repositories, projections, specifications, custom queries, service transactions, migrations, indexes, data volume assumptions, and repository tests.
- Controller/API call sites when queries back public list endpoints.
- Existing fetch, pagination, locking, and migration conventions.

# Step-by-step workflow

1. Identify the use case and expected data volume.
2. Check query boundedness, pagination, sorting, and projection needs.
3. Check entity relationships, fetch strategy, N+1 risk, and lazy-loading at API boundaries.
4. Check transaction boundaries and write consistency.
5. Check indexes, uniqueness constraints, and migration/backfill needs.
6. Check optimistic locking or idempotency for concurrent updates.
7. Check custom query tests with realistic data.
8. Rank findings by correctness, data integrity, and performance impact.

# Java best practices

- Keep repository method names readable; use `@Query` or specifications when names become unclear.
- Use `Optional<T>` for expected absence.
- Keep mapping from entities to DTOs explicit at service/read-model boundaries.
- Avoid building JPQL/SQL through untrusted string concatenation.

# Spring Boot best practices

- Keep persistence access in repositories or data adapters.
- Put transaction boundaries in services/use cases.
- Use `Page`, `Slice`, projections, fetch joins, or `@EntityGraph` deliberately.
- Prefer lazy relationships by default and fetch what the use case needs.
- Do not expose JPA entities through REST.

# Multi-stack adaptation notes

- If the repo uses multiple stores, do not apply JPA assumptions to MongoDB, Cosmos DB, Redis, or external SDK models.
- For read-heavy endpoints, consider query-specific DTOs or projections.
- For batch jobs, consider chunking and transaction size.
- For reactive stacks, do not mix blocking JPA calls into reactive service logic without a deliberate boundary.

# Thread-safety rules

- JPA entities and `EntityManager` are not safe shared mutable state across threads.
- Avoid using entities as cached singleton fields.
- Handle concurrent updates through database constraints, optimistic locking, pessimistic locks, or idempotency keys where appropriate.

# Configuration-driven design rules

- Keep batch sizes, page limits, query timeouts, and feature toggles configurable when they vary by environment.
- Do not hardcode schema names, tenant IDs, or environment-specific database details.
- Use migration tools and naming conventions already present in the repo.

# Testing requirements

- Use `@DataJpaTest` or repository test style used by the project for custom queries.
- Flush and clear when proving database behavior.
- Cover pagination, empty results, filters, sorting, duplicate/unique constraints, and locking conflicts when relevant.
- Add migration/integration tests only when the target repo supports them.

# Few-shot examples

1. Good: pageable repository query in `examples/practical-patterns.good.md`.
2. Good: projection for list endpoint in `examples/practical-patterns.good.md`.
3. Good: `@EntityGraph` for required association in `examples/practical-patterns.good.md`.
4. Good: optimistic lock on concurrent aggregate in `examples/practical-patterns.good.md`.
5. Good: repository test flushes and clears in `examples/practical-patterns.good.md`.
6. Bad: `findAll()` then in-memory filter in `examples/review-traps.bad.md`.
7. Bad: entity returned from controller in `examples/review-traps.bad.md`.
8. Bad: broad `EAGER` relationship for one endpoint in `examples/review-traps.bad.md`.
9. Bad: string-concatenated JPQL in `examples/review-traps.bad.md`.
10. Bad: destructive migration without staging in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- Query is bounded and indexed where needed.
- Fetch plan is deliberate.
- Transaction scope is in service/use-case code.
- Entity models do not cross REST boundaries.
- Concurrency and idempotency are considered.
- Repository tests cover custom behavior.

# Final response expectations

- Findings ordered by severity.
- Data integrity, performance, migration, and test risks.
- Exact commands run or skipped validation.
