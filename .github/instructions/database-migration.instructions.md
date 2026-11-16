---
applyTo: "**/*{Migration,Changelog}*.{sql,xml,yaml,yml,json},**/db/migration/**/*,**/migrations/**/*"
description: "Database migration rules for safe rollout, backfill, indexes, compatibility, and non-destructive changes."
---

# Purpose

Guide safe schema evolution for Spring Boot services across rolling deployments and existing production data.

# Rules

- Add new migrations instead of editing migrations that may already have been applied.
- Design non-null column rollout safely: add nullable column, backfill, deploy code, then enforce not-null later when needed.
- Include a backfill strategy for existing rows.
- Add indexes for new frequent filters, joins, uniqueness checks, and sort patterns.
- Think in forward fixes as well as rollback, especially when destructive rollback is unsafe.
- Keep migrations compatible with rolling deployments when old and new application versions may run together.
- Avoid destructive migrations by default.
- Do not rely on Hibernate `ddl-auto` for controlled schema evolution unless the target project explicitly does.

# Good examples

```sql
alter table orders add column cancellation_reason varchar(255);

update orders
set cancellation_reason = 'UNKNOWN'
where status = 'CANCELLED' and cancellation_reason is null;
```

```sql
create index idx_orders_customer_status on orders (customer_id, status);
```

# Bad examples

```sql
-- Bad: fails or locks unexpectedly when existing rows have no value.
alter table orders add column cancellation_reason varchar(255) not null;
```

```sql
-- Bad: destructive by default and unsafe for rolling deployments.
drop column legacy_status from orders;
```

# Good Copilot behavior

- Inspect the migration tool, naming convention, existing migration style, and deployment expectations.
- Split risky changes into expand/backfill/contract steps.
- Add repository or integration tests when schema changes affect queries.

# Bad Copilot behavior

- Editing an old migration to change production history.
- Adding a new query without considering an index.
- Dropping data because current code no longer uses it.

# Checklist

- Migration is additive or safely staged.
- Existing data has a backfill path.
- New query patterns have indexes when needed.
- Rolling deployment compatibility was considered.
- Destructive changes are avoided or explicitly justified.

# Few-shot mini examples

1. Good: add nullable column, backfill, then enforce `not null` in a later migration.
2. Good: create an index before deploying a query that depends on it.
3. Good: make enum/string additions backward-compatible with old app versions.
4. Good: document rollback risk for irreversible data transforms.
5. Good: keep migration names consistent with Flyway or Liquibase conventions in the repo.
6. Bad: edit an already-applied production migration.
7. Bad: drop a column in the same deploy that stops writing it.
8. Bad: add `not null` column without default/backfill for existing rows.
9. Bad: run data cleanup that assumes small tables without batching.
10. Bad: add a query-heavy feature without considering index impact.
