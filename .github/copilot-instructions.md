# Copilot Repository Instructions

## Purpose

This repository stores reusable GitHub Copilot instructions, prompts, agents, and skills for Java Spring Boot projects.

It is focused on GitHub Copilot, GitHub Copilot Chat, GitHub Copilot coding agent, VS Code with GitHub Copilot, and JetBrains IntelliJ IDEA with GitHub Copilot.

## Global Rules

- Inspect existing code, tests, configuration, and docs before editing.
- Follow the target repository conventions before applying generic preferences.
- Do not invent dependencies, commands, APIs, environment variables, infrastructure, architecture, or business rules.
- Keep diffs focused, reviewable, and tied to the user request.
- Add or update tests for behavior changes.
- Run the smallest relevant tests when possible.
- Do not claim tests passed unless they actually ran.
- Never expose secrets, tokens, passwords, private keys, PII, cookies, or authorization headers.
- Use generic examples only, such as `Order`, `Customer`, `Payment`, `Invoice`, `Product`, and `Shipment`.
- Do not leave copied repository identifiers, package names, organization names, internal domains, or hardcoded GitHub URLs.
- Prefer placeholders when the target repository must decide: `{{PROJECT_NAME}}`, `{{SERVICE_NAME}}`, `{{BASE_PACKAGE}}`, `{{BUILD_TOOL}}`, `{{TEST_COMMAND}}`, and `{{RUN_COMMAND}}`.

## Spring Boot Defaults

- Prefer constructor injection over field injection.
- Prefer `private final` dependencies; if the target project already uses Lombok, `@RequiredArgsConstructor` is acceptable for bean injection.
- Keep controllers thin: HTTP binding, validation trigger, status codes, and delegation only.
- Use DTOs for REST boundaries.
- Use Bean Validation for request DTOs, path variables, query parameters, and configuration properties when relevant.
- Put business logic, authorization decisions that need domain data, and transaction boundaries in services or domain code.
- Keep repositories persistence-focused.
- Do not expose JPA, MongoDB, Cosmos DB, or other persistence models directly from REST APIs.
- Use explicit error handling through the project's existing exception and error response conventions.
- Prefer `@ConfigurationProperties` over scattered `@Value` fields.
- Prefer configuration-driven thresholds, toggles, retry settings, timeout settings, integration names, feature flags, and strategy selection over hardcoded literals.
- Use pagination or documented bounds for collection endpoints.
- Avoid lazy-loading through JSON serialization.
- Keep Spring singleton beans stateless and thread-safe; never store request/user-specific state in fields.
- Choose guidance by the target stack: servlet, WebFlux, virtual threads, JPA, MongoDB, Cosmos DB, Azure SDK clients, Azure Service Bus, Spring Cloud Stream, Redis/cache, scheduled jobs, or message-driven services.
- Prefer clear, testable Java over clever abstractions.

## Java Defaults

- Write Java 17-compatible code unless the target project clearly allows newer Java features.
- Java 21 features are welcome only when the target repository supports them.
- Use meaningful names and cohesive methods.
- Prefer immutability where it improves clarity.
- Use records for immutable DTOs when compatible with the project style.
- Use `Optional` mainly as a return type, not as a field or parameter.
- Compare `BigDecimal` business values with `compareTo`, not `equals`.
- Avoid floating point types for money.
- Avoid mutable shared state and be cautious with parallel streams.
- Prefer guard clauses over deep nesting.
- Avoid magic strings and magic numbers; extract repeated literals, event names, log templates, and thresholds into constants or typed configuration.
- Use structured logging placeholders instead of string concatenation; use `String.formatted(...)` when building formatted strings is clearer outside logging.
- Add interfaces, strategies, factories, adapters, or handler registries only when there are multiple implementations, expected variability, external boundaries, or clear testability benefits.

## Multi-Stack Defaults

- Servlet/MVC code should not accidentally depend on reactive APIs, and reactive code should not block.
- In WebFlux services, do not call `.block()` inside service logic and do not manually call `.subscribe()` unless there is a deliberate boundary.
- Use `StepVerifier` for reactive unit tests.
- For virtual threads, inspect target library and driver compatibility before assuming blocking code is safe.
- For JPA, use pagination, avoid N+1 queries, consider `Slice` when a count query is expensive, and consider optimistic locking for concurrent updates.
- For MongoDB and Cosmos DB, design queries with indexes, partition keys, projections, continuation tokens, and schema evolution in mind.
- For Azure SDK, Service Bus, and Stream Binder integrations, make clients Spring beans, make resource names/retries/timeouts configuration-driven, validate payloads, design handlers as idempotent, and understand retry/DLQ behavior.
- Never log secrets, connection strings, tokens, authorization headers, or sensitive message payloads.

## Testing Defaults

- Aim for meaningful coverage above 90% for new or changed code, but do not chase coverage with meaningless tests.
- Prefer focused unit tests for business logic and avoid `@SpringBootTest` unless full Spring wiring or integration is truly required.
- Use the target repository's JUnit, Mockito, AssertJ, MockMvc, WebTestClient, StepVerifier, DataJpaTest, Mongo, and Testcontainers conventions.
- Unit test method names should follow `methodName_input_expectation`, for example `cancelOrder_shippedOrder_throwInvalidOrderStateException`.
- Add regression tests for bug fixes, boundary tests for thresholds, and negative tests for validation and authorization.

## When Adding Content To This Kit

- Every prompt must include purpose, user input template, context gathering, workflow, tests, self-review, final response, and few-shot examples.
- Every skill must include trigger frontmatter, when to use, when not to use, context to inspect, workflow, Java practices, Spring Boot practices, multi-stack adaptation notes, thread-safety rules, configuration-driven design rules, testing, at least ten few-shot examples or references, checklist, and final output expectations.
- Important skills should include `checklist.md` and `examples/` files with both good and bad patterns.
- Every agent must include role, use cases, inputs, required files to inspect, relevant instructions and skills, behavior, checklist, output format, good findings, and bad patterns.
- Every instruction file should include purpose, rules, good examples, bad examples, good Copilot behavior, bad Copilot behavior, checklist, and at least ten practical mini examples.
- Keep guidance short enough for Copilot to use, but concrete enough to shape behavior.

## Validation Defaults

Use the target repository's actual build tool and documented commands. If commands are unknown, inspect files such as `pom.xml`, `build.gradle`, `build.gradle.kts`, `README.md`, and CI workflows before suggesting or running a command.

Prefer the narrowest useful validation first, for example:

```bash
{{TEST_COMMAND}}
```

For broader changes, run the relevant suite only when it exists and is practical:

```bash
{{BUILD_TOOL}} test
```

If formatting or linting tools exist, run the repository's configured command. Do not assume Gradle, Maven, Spotless, Checkstyle, PostgreSQL, Lombok, MapStruct, Flyway, Liquibase, Testcontainers, OAuth2, or a specific Spring Boot patch version unless the target repository shows it.

## Final Response

Summarize:

- Files changed.
- Relevant instruction groups, prompts, skills, or agents used.
- Tests or commands run, with results.
- Assumptions, skipped checks, and remaining risks.
