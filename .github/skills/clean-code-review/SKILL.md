---
name: clean-code-review
description: Use when reviewing Java and Spring Boot code for correctness risk, readability, cohesion, naming, over-engineering, layer boundaries, duplication, thread safety, and maintainability.
---

# Skill

Clean code review playbook for Java Spring Boot services.

# When to use

- Reviewing PRs or local changes for maintainability and correctness risk.
- Refactoring small areas while preserving behavior.
- Checking Java readability, Spring layer boundaries, duplication, naming, and abstraction choices.
- Reviewing whether a design is appropriately simple for the target repository.

# When not to use

- Security-only reviews better handled by `security-review`.
- Persistence performance reviews better handled by `jpa-persistence-review`.
- Pure formatting changes where formatter output is the only concern.

# Required context to inspect

- Changed files, nearby code, tests, public interfaces, package conventions, and existing abstractions in the same feature area.
- Existing instruction files for style, layering, dependency injection, reusable architecture, and thread safety.
- Callers and downstream effects when reviewing public methods.

# Step-by-step workflow

1. Read the diff and surrounding code before judging style.
2. Look for correctness, behavior, and test gaps before naming nits.
3. Check names, cohesion, method length, duplication, and control flow.
4. Check Spring layer boundaries, dependency direction, and transaction placement.
5. Check whether abstractions remove real complexity or merely add indirection.
6. Check mutable shared state and thread-safety in singleton beans.
7. Check whether configuration belongs in typed properties instead of literals.
8. Rank findings by severity with concrete examples.

# Java best practices

- Prefer readable guard clauses over deep nesting.
- Keep methods cohesive and names intent-revealing.
- Use `Optional` mainly as a return type.
- Use `BigDecimal.compareTo` for money comparisons.
- Prefer simple loops over stream pipelines that hide branching, logging, mutation, or exceptions.
- Add abstractions only for real variability, external boundaries, or clear testability.

# Spring Boot best practices

- Controllers thin, services cohesive, repositories persistence-focused.
- Constructor injection and immutable dependencies.
- DTOs at REST boundaries.
- Transactions in service/use-case layer.
- Configuration via typed properties when values vary by environment.

# Multi-stack adaptation notes

- MVC and WebFlux should not be mixed accidentally.
- JPA entities, Mongo documents, Cosmos documents, external SDK models, and API DTOs should stay separated.
- Messaging and scheduled jobs should keep idempotency and retry behavior visible.
- Cache code should define keys, TTL, invalidation, and serialization intentionally.

# Thread-safety rules

- Flag request/user/tenant state stored in singleton fields.
- Prefer immutable collaborators and local variables.
- Avoid static mutable helpers and caches without ownership, limits, and synchronization.
- Check async code for lost transaction/security context.

# Configuration-driven design rules

- Move thresholds, toggles, timeout values, retry settings, and external resource names out of business code when they may vary.
- Avoid scattered `@Value`; prefer grouped `@ConfigurationProperties`.
- Do not turn every constant into config when it is a true domain invariant.

# Testing requirements

- Behavior changes need tests.
- Refactors should preserve or improve existing coverage.
- Tests should assert observable behavior, not only mock calls.
- Add regression tests when review finds a likely bug.

# Few-shot examples

1. Good: guard clause for invalid state in `examples/practical-patterns.good.md`.
2. Good: cohesive service method with clear dependency direction in `examples/practical-patterns.good.md`.
3. Good: strategy list used only for real variants in `examples/practical-patterns.good.md`.
4. Good: readable loop instead of tortured stream in `examples/practical-patterns.good.md`.
5. Good: named constant or config for repeated value in `examples/practical-patterns.good.md`.
6. Bad: generic processor abstraction without use cases in `examples/review-traps.bad.md`.
7. Bad: controller with repository and business logic in `examples/review-traps.bad.md`.
8. Bad: static mutable utility cache in `examples/review-traps.bad.md`.
9. Bad: stream pipeline hides database calls in `examples/review-traps.bad.md`.
10. Bad: `Optional` field in DTO in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- Findings prioritize behavior and risk.
- Names and control flow are clear.
- Classes and methods are cohesive.
- Layer boundaries are preserved.
- Abstractions are justified.
- Mutable state is thread-safe or avoided.
- Tests protect behavior.

# Final response expectations

- Findings first, ordered by severity.
- File/line references when available.
- Open questions.
- Brief summary and test gaps.
