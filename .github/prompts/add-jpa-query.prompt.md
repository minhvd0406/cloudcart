# Purpose

Add or modify a JPA repository query with pagination, fetch strategy, index awareness, and repository tests.

# User input template

```text
Query need:
Entity/repository:
Filters/sort:
Expected result shape:
Cardinality:
Known performance concerns:
Preferred test command:
```

# Required context gathering

- Inspect entity mappings, repository conventions, existing queries, service callers, migrations, and repository tests.
- Check whether the query backs a REST list endpoint.
- Look for existing indexes or migration tool conventions.
- Confirm the repository actually uses JPA; if it uses MongoDB, Cosmos DB, or another store, switch to the matching data-access guidance.

# Relevant instructions

- `data-access.instructions.md`
- `performance.instructions.md`
- `database-migration.instructions.md`
- `testing.instructions.md`
- `mongodb-cosmosdb.instructions.md`
- `multi-stack-spring.instructions.md`

# Relevant skills

- `jpa-persistence-review`
- `spring-test-strategy`

# Suggested agent

`persistence-jpa`

# Workflow

1. Choose query method, `@Query`, specification, projection, or entity graph based on clarity and performance.
2. Add `Pageable` for growing result sets.
3. Use `Slice` when next-page detection is enough and count queries are expensive.
4. Fetch required associations deliberately.
5. Consider migration/index needs.
6. Add `@DataJpaTest` or repository test coverage.

# Testing workflow

- Test filters, sorting, pagination, empty result, and association fetching when relevant.
- Flush and clear when proving persistence behavior.
- Aim for meaningful coverage of new query behavior and edge cases.

# Stop and ask when

- The data stack is not JPA.
- The query requires a new index/migration strategy that is unclear.
- Expected data volume or sorting behavior is missing.
- A new dependency or database feature is needed.
- Validation command cannot be determined safely.

# Self-review checklist

- No unbounded `findAll()` for growing data.
- No lazy loading through serialization.
- Query is type-safe and parameterized.
- Index need is addressed or noted.

# Expected final response

- Query added or changed.
- Tests run and results.
- Index/migration considerations.
- Remaining performance risks.

# Few-shot examples

## Example request

Add JPA query by `customerId` and `status` with pagination.

## Good Copilot behavior

- Add `Page<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status, Pageable pageable)`.
- Add repository test for filtering and page size.
- Consider index on `(customer_id, status)`.

## Good final response outline

- Added paginated repository query.
- Added `DataJpaTest` coverage.
- Noted migration/index follow-up if no index exists.

## Bad Copilot behavior to avoid

- Returning `List<Order>` for unbounded data.
- Loading all rows then filtering in memory.
- Adding `EAGER` relationships to hide N+1.
