---
name: spring-boot-feature
description: Use when implementing or modifying focused Spring Boot backend behavior across controllers, services, repositories, DTOs, validation, error handling, configuration, and tests.
---

# Skill

Spring Boot feature implementation playbook for small, production-shaped backend changes.

# When to use

- Adding or modifying a focused backend feature.
- Creating or changing REST endpoints, DTOs, service behavior, repository queries, validation, exceptions, configuration, or tests.
- Refactoring feature code while preserving behavior and public contracts.
- Turning a vague feature request into a narrow vertical slice that matches the target repository.

# When not to use

- Pure documentation-only tasks.
- Broad architecture redesign without a specific feature.
- Frontend-only work.
- Replacing the target repository's stack, package layout, build tool, or test framework without explicit approval.

# Required context to inspect

- Existing controller, DTO, service/use case, repository, entity/document, mapper, exception handler, configuration properties, and tests for the feature area.
- Build files, README, CI workflow, and available test commands before running commands.
- Security configuration and current-user patterns when endpoint access changes.
- Migration files and index definitions when persistence shape or query patterns change.
- Existing instruction files for the active stack: MVC, WebFlux, JPA, MongoDB, Cosmos DB, messaging, cache, scheduled jobs, Azure SDK, or virtual threads.

# Step-by-step workflow

1. Restate the requested behavior, compatibility constraints, and assumptions.
2. Inspect target repository conventions before adding files or patterns.
3. Identify the smallest vertical slice that implements the behavior.
4. Keep HTTP binding in controllers and business decisions in service/domain code.
5. Keep persistence access in repositories or data-access adapters.
6. Add Bean Validation, explicit domain errors, and central error mapping when needed.
7. Move thresholds, toggles, retry settings, timeout settings, external resource names, and strategy selection into typed configuration when they may vary.
8. Keep Spring singleton beans stateless and avoid request/user-specific fields.
9. Add focused tests at the layer where behavior lives.
10. Run the smallest relevant validation command and report exact results.

# Java best practices

- Prefer clear names, cohesive methods, guard clauses, and simple control flow.
- Use records for immutable DTOs when compatible with the project style.
- Use `BigDecimal.compareTo` for money thresholds and avoid floating point money.
- Use `Optional` mainly as a return type, not as a field or parameter.
- Add interfaces, strategies, or registries only when multiple implementations or real variability exists.
- Prefer immutable collections or defensive copies for DTOs and value objects.

# Spring Boot best practices

- Constructor injection over field injection.
- DTOs at REST boundaries; never expose persistence entities or documents through REST.
- Bean Validation for request DTOs, path/query parameters, and configuration properties.
- Service-layer transactions, with `readOnly = true` for reads when appropriate.
- No repository calls from controllers.
- Pagination or documented bounds for growing collections.
- Preserve existing error, mapping, versioning, and security conventions.

# Multi-stack adaptation notes

- MVC: use controllers, DTOs, MockMvc/WebMvc tests, and existing error handlers.
- WebFlux: return `Mono`/`Flux`, compose with reactive operators, and avoid `.block()` or hidden `.subscribe()`.
- JPA: watch transaction boundaries, lazy loading, N+1, optimistic locking, projections, and migrations.
- MongoDB/Cosmos DB: use indexes, partition keys, projections, continuation tokens, and schema-version-safe documents.
- Messaging/scheduled jobs: design idempotent handlers and understand retry/DLQ or replay behavior.
- Azure/cloud clients: create clients as beans and configure endpoints, resource names, retries, and timeouts.

# Thread-safety rules

- Treat Spring services, controllers, repositories, mappers, and configuration beans as singleton-scoped unless the target repo shows otherwise.
- Do not store request, user, tenant, correlation, or mutable workflow state in fields.
- Use local variables, immutable values, database constraints, optimistic locks, or explicit synchronization mechanisms when concurrency matters.
- Avoid `parallelStream()` for request-scoped, database, remote-call, or security-context work.

# Configuration-driven design rules

- Prefer typed `@ConfigurationProperties` for grouped settings.
- Validate required configuration with Bean Validation.
- Use defaults only when they are safe and documented by the target repo.
- Do not hardcode environment names, cloud resources, tenant IDs, URLs, credentials, queue/topic names, thresholds, or timeout values.
- Keep business variants in strategy/handler selection only when the variability is real and likely to grow.

# Testing requirements

- Unit tests for business rules and state transitions.
- MockMvc/WebMvc or WebTestClient tests for REST behavior.
- `@DataJpaTest` or equivalent repository tests for custom queries.
- Regression tests for bug fixes.
- Boundary tests for thresholds and date/time rules.
- Negative tests for validation, conflicts, not-found, and authorization when relevant.
- New unit test names should follow `methodName_input_expectation` when compatible.

# Few-shot examples

1. Good: thin controller delegates creation to a service in `examples/practical-patterns.good.md`.
2. Good: service owns cancellation transaction in `examples/practical-patterns.good.md`.
3. Good: typed configuration drives VIP threshold behavior in `examples/practical-patterns.good.md`.
4. Good: strategy list replaces growing payment-type branching in `examples/practical-patterns.good.md`.
5. Good: focused unit test covers threshold boundary in `examples/practical-patterns.good.md`.
6. Bad: controller calls repository and mutates entity in `examples/review-traps.bad.md`.
7. Bad: request DTO accepts server-managed fields in `examples/review-traps.bad.md`.
8. Bad: mutable singleton field stores request state in `examples/review-traps.bad.md`.
9. Bad: hardcoded cloud queue and timeout in service code in `examples/review-traps.bad.md`.
10. Bad: feature test starts full context for pure logic in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- Existing conventions inspected.
- Controller remains thin.
- DTOs protect API boundary.
- Service/domain owns behavior and transaction scope.
- Persistence access is bounded and stays out of controllers.
- Configuration is typed and validated when behavior may vary.
- Spring beans remain stateless and thread-safe.
- Tests cover changed behavior, boundaries, and important negative paths.
- Commands run are reported honestly.

# Final response expectations

- Files changed and behavior implemented.
- Tests/commands run with exact results.
- Assumptions, compatibility notes, and remaining risks.
- Any skipped validation with a clear reason.
