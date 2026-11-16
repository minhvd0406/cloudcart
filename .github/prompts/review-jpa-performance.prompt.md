# Purpose

Review JPA and data-access changes for N+1 queries, missing pagination, inefficient fetching, transaction scope, document-store risks, and index/migration needs.

# User input template

```text
JPA/database change:
Diff/files:
Expected data volume:
Known slow path:
Tests run by author:
```

# Required context gathering

- Inspect repositories/templates, entities/documents, service callers, migrations, indexes, partition keys, query tests, and endpoint pagination.
- Check fetch strategy and serialization boundaries.
- Check whether the query is used in loops or public list endpoints.
- Identify whether the target stack is JPA, MongoDB, Cosmos DB, Redis/cache, or another store.

# Relevant instructions

- `data-access.instructions.md`
- `performance.instructions.md`
- `transactions.instructions.md`
- `database-migration.instructions.md`
- `mongodb-cosmosdb.instructions.md`
- `multi-stack-spring.instructions.md`

# Relevant skills

- `jpa-persistence-review`
- `observability-review`

# Suggested agent

`persistence-jpa`

# Workflow

1. Identify data stack, query shape, and cardinality.
2. Check pagination and bounds.
3. Check association fetching and N+1 risks for JPA.
4. Check indexes, partition keys, continuation tokens, projections, or migrations for new filters.
5. Check transaction scope, lazy loading, and serialization.
6. Rank findings by severity.

# Testing workflow

- Look for `@DataJpaTest` coverage.
- Suggest JPA, Mongo, Cosmos, pagination, query, and mapping tests where missing.
- Do not claim query plans were checked unless they were.

# Stop and ask when

- Data stack or expected data volume is unclear.
- Index, partition key, or migration strategy is needed but not specified.
- Real database/cloud verification would be required.

# Self-review checklist

- No unbounded public reads.
- No entity exposure to REST.
- Fetching is deliberate.
- Index need is noted.

# Expected final response

- Findings ordered by severity.
- Query/test gaps.
- Index or migration considerations.
- Residual performance risk.

# Few-shot examples

## Example request

Review JPA query for N+1 and missing pagination.

## Good Copilot behavior

- Flag `findAll()` followed by in-memory filtering.
- Suggest paginated query by `customerId` and `status`.
- Note index on `(customer_id, status)`.

## Good final response outline

- High: unbounded `findAll()` on public endpoint.
- Medium: missing index for new filter.
- Test gap: no repository pagination test.

## Bad Copilot behavior to avoid

- Suggesting `EAGER` relationships as a default fix.
- Adding cache before query shape is fixed.
- Ignoring migration implications.
