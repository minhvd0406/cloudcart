---
name: spring-test-strategy
description: Use when choosing, writing, or reviewing focused Spring Boot tests across unit, MockMvc/WebMvc, WebTestClient, DataJpaTest, integration, security, regression, and coverage scenarios.
---

# Skill

Spring Boot testing strategy playbook for meaningful confidence without oversized test scopes.

# When to use

- Adding tests for new or changed behavior.
- Choosing between unit, slice, repository, integration, contract, and security tests.
- Reviewing test quality, reliability, coverage, or missing regression cases.
- Replacing hollow coverage with behavior-focused tests.

# When not to use

- Documentation-only tasks.
- Manual QA plans unrelated to code behavior.
- Performance benchmarking that needs specialized tooling beyond repository conventions.

# Required context to inspect

- Changed code, nearby tests, fixtures, builders, security helpers, build files, CI workflows, and test configuration.
- Existing JUnit, Mockito, AssertJ, Hamcrest, MockMvc, WebTestClient, StepVerifier, DataJpaTest, Mongo, Cosmos, and Testcontainers conventions.
- Coverage configuration or reports when present.
- Preferred validation command from README, CI, or build scripts.

# Step-by-step workflow

1. Identify behavior, risk, and user-visible outcome.
2. Choose the smallest test layer that proves the behavior.
3. Cover happy path, negative path, boundaries, and permissions where relevant.
4. Avoid mocking the system under test.
5. Avoid assertions that only prove a mock was called unless the interaction is the observable contract.
6. Keep fixtures deterministic and readable.
7. Name new unit tests with `methodName_input_expectation` when compatible.
8. Aim for meaningful coverage above 90% for new or changed code without padding.
9. Run focused tests first and broaden only when shared wiring changed.
10. Report commands and results exactly.

# Java best practices

- Use deterministic clocks, fixed IDs, stable test data, and clear builders.
- Prefer AssertJ/JUnit assertions according to project style.
- Avoid sleeps, time-order assumptions, and test interdependence.
- Keep tests readable enough to explain the business rule.

# Spring Boot best practices

- Use pure unit tests for business logic.
- Use `@WebMvcTest` or MockMvc for servlet controller behavior.
- Use WebTestClient and StepVerifier for reactive behavior.
- Use `@DataJpaTest` for JPA queries and mappings.
- Use Mongo/Cosmos-focused tests for document queries and partition key behavior.
- Use `@SpringBootTest` only when full wiring, security chain, configuration, or infrastructure integration matters.
- Use Testcontainers only when already supported or clearly required.

# Multi-stack adaptation notes

- MVC validation and error tests should assert status and body shape.
- WebFlux tests should avoid blocking and verify reactive completion or errors.
- JPA tests should flush and clear when proving database behavior rather than persistence-context caching.
- Messaging tests should cover idempotency, retry, poison-message, and DLQ behavior when supported locally.
- Azure/cloud tests should avoid real external resources unless the target repo explicitly provides safe test infrastructure.

# Thread-safety rules

- Tests must not depend on execution order or mutable static state.
- Reset shared fixtures between tests.
- Avoid parallel test assumptions unless the project already supports them.
- For concurrency behavior, assert idempotency or conflict handling with deterministic synchronization.

# Configuration-driven design rules

- Override properties with test-specific configuration mechanisms already used by the repo.
- Do not depend on developer-machine environment variables for ordinary tests.
- Use fixed clocks, test profiles, and local ports/resources that avoid production systems.

# Testing requirements

- Regression tests for bug fixes.
- Boundary tests for thresholds, dates, pagination sizes, and numeric rules.
- Negative tests for validation, authorization, conflicts, and missing resources.
- Repository tests for custom queries.
- Contract tests for public API changes.
- Security tests for `401`, `403`, and ownership when relevant.

# Few-shot examples

1. Good: pure unit test for shipped cancellation in `examples/practical-patterns.good.md`.
2. Good: boundary test for VIP threshold in `examples/practical-patterns.good.md`.
3. Good: MockMvc validation test asserts error field in `examples/practical-patterns.good.md`.
4. Good: repository test flushes before assertion in `examples/practical-patterns.good.md`.
5. Good: reactive test uses StepVerifier in `examples/practical-patterns.good.md`.
6. Bad: full Spring context for pure branch logic in `examples/review-traps.bad.md`.
7. Bad: test only verifies repository save in `examples/review-traps.bad.md`.
8. Bad: current time makes test flaky in `examples/review-traps.bad.md`.
9. Bad: test hits production-like external resource in `examples/review-traps.bad.md`.
10. Bad: coverage padding with getter tests in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- Correct test layer chosen.
- Behavior is observable.
- Boundaries and negative paths covered.
- Test data is deterministic.
- Spring context is avoided unless needed.
- New unit test names follow `methodName_input_expectation` when compatible.
- New/changed code has meaningful coverage.
- Commands are reported accurately.

# Final response expectations

- Tests added or reviewed.
- Scenarios covered and remaining gaps.
- Commands run and results.
- Any infrastructure assumptions or skipped checks.
