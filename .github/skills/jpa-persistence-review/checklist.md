# JPA Persistence Review Checklist

- Inspect entities, repositories, projections, queries, transactions, migrations, indexes, and tests.
- Confirm list queries are paginated or bounded.
- Confirm filtering/sorting happens in the database when data can grow.
- Confirm required associations are fetched deliberately.
- Confirm REST APIs do not expose entities.
- Confirm repository method names remain readable.
- Confirm custom JPQL/native SQL is parameterized.
- Confirm new query patterns consider indexes.
- Confirm migrations are additive or safely staged.
- Confirm concurrent updates use optimistic locking, constraints, or idempotency where needed.
- Confirm repository tests cover filters, sorting, pagination, and empty results.
