---
applyTo: "**/*{Mongo,Cosmos,Document,Repository,Template,Query,ChangeFeed,Container}.java,**/application*.{yml,yaml,properties}"
description: "MongoDB, MongoTemplate, and Cosmos DB rules for indexes, projections, pagination, partition keys, RU-aware queries, and schema evolution."
---

# Purpose

Guide safe document-database access for Spring Boot services using MongoDB, MongoTemplate, Cosmos DB, or similar document stores.

# Rules

- Build explicit queries with indexes in mind.
- Avoid unbounded scans and cross-partition queries.
- Use projections when only partial fields are needed.
- Use pagination, continuation tokens, or bounded batch processing for large results.
- Construct queries safely; never concatenate untrusted query fragments.
- Plan backward-compatible document schema evolution.
- For Cosmos DB, choose and use partition keys deliberately.
- For Cosmos DB, design RU-aware queries and avoid accidental fan-out.
- Configure SDK retries, timeouts, consistency, database/container names, and connection settings through typed properties.
- Do not expose document classes directly through REST APIs.

# Good examples

```java
Query query = Query.query(Criteria.where("customerId").is(customerId)
        .and("status").is(status))
        .with(pageable);
query.fields().include("id").include("status").include("total");
```

```java
record OrderDocument(
        String id,
        String customerId,
        String status,
        Integer schemaVersion) {
}
```

# Bad examples

```java
// Bad: unbounded scan and in-memory filtering.
List<OrderDocument> documents = mongoTemplate.findAll(OrderDocument.class).stream()
        .filter(order -> order.customerId().equals(customerId))
        .toList();
```

```java
// Bad: Cosmos query ignores partition key and may fan out across partitions.
cosmosTemplate.find("select * from c where c.status = 'PAID'", OrderDocument.class);
```

# Good Copilot behavior

- Inspect indexes, partition keys, repository patterns, and document schema versioning.
- Add repository/template tests for filters and pagination.
- Call out migration/backfill needs for document schema changes.

# Bad Copilot behavior

- Assuming JPA patterns fit MongoDB or Cosmos DB.
- Returning document models from REST controllers.
- Adding broad scans because the sample data is small.

# Checklist

- Query is indexed or index need is noted.
- Results are bounded.
- Partition key is used when relevant.
- Document schema changes are backward-compatible.
- DTOs protect API boundaries.

# Few-shot mini examples

1. Good: query by indexed `customerId` and `status` with pagination.
2. Good: include only `id`, `status`, and `total` when building summaries.
3. Good: include partition key in Cosmos DB point reads or scoped queries.
4. Good: add `schemaVersion` when document shape evolves.
5. Good: document backfill needs for new required document fields.
6. Bad: use `mongoTemplate.findAll()` then filter in memory.
7. Bad: run a cross-partition Cosmos query for a high-volume endpoint.
8. Bad: return document classes directly from REST controllers.
9. Bad: concatenate user input into JSON query fragments.
10. Bad: assume JPA lazy-loading or transactions work the same for documents.
