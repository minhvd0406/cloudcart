---
name: validation-error-handling
description: Use when reviewing or implementing Bean Validation, request validation, configuration validation, domain exceptions, ProblemDetail/error contracts, and negative API tests.
---

# Skill

Validation and error-handling playbook for Spring Boot services.

# When to use

- Adding or reviewing request, path, query, message, file, or configuration validation.
- Mapping domain exceptions to API errors.
- Designing validation error response shape and negative tests.
- Replacing `null`, `false`, or catch-all failures with explicit domain outcomes.

# When not to use

- Pure persistence query review with no validation or error behavior.
- Security-only authorization reviews.
- Changing public error contracts without compatibility context.

# Required context to inspect

- DTOs, controllers, exception handlers, validation annotations, custom validators, service exceptions, error response tests, and API docs.
- Existing `ProblemDetail` or custom error format.
- Configuration properties and startup validation when config changes.

# Step-by-step workflow

1. Identify external inputs and expected failure modes.
2. Check validation at the boundary closest to the input.
3. Check domain exceptions for expected business failures.
4. Check central error mapping and safe messages.
5. Check field-level validation response shape.
6. Check that internals, stack traces, SQL, provider payloads, and secrets are not exposed.
7. Add negative tests for invalid, missing, conflicting, and not-found cases.

# Java best practices

- Use clear domain exceptions or result types for expected failures.
- Avoid `null` returns to signal business failure.
- Keep validation messages useful but not sensitive.
- Keep custom validators deterministic and side-effect free.

# Spring Boot best practices

- Use Bean Validation on DTOs, method parameters, and configuration properties where appropriate.
- Use `@ControllerAdvice` for REST error mapping.
- Use `ProblemDetail` or the repository's established error shape.
- Map not found to `404`, validation to `400`, conflicts to `409`, and forbidden access to `403`.

# Multi-stack adaptation notes

- MVC uses `MethodArgumentNotValidException` and parameter validation conventions.
- WebFlux uses reactive validation/error handling without blocking.
- Messaging handlers should validate payloads before side effects.
- Configuration validation should fail fast at startup.

# Thread-safety rules

- Custom validators used as beans must be stateless or use thread-safe collaborators.
- Do not store the last validation request or error list in fields.
- Avoid mutable static error builders.

# Configuration-driven design rules

- Validate typed configuration with Bean Validation.
- Keep limits, thresholds, patterns, and feature toggles in config when they may vary.
- Avoid hardcoded environment-specific validation values.

# Testing requirements

- Controller or WebTestClient tests for invalid request body, invalid path/query input, and malformed JSON when relevant.
- Service tests for domain conflicts and not-found behavior.
- Tests should assert status and error shape.
- Configuration validation tests when new required properties are added.

# Few-shot examples

1. Good: nested request validation in `examples/practical-patterns.good.md`.
2. Good: domain conflict exception in `examples/practical-patterns.good.md`.
3. Good: central `ProblemDetail` mapping in `examples/practical-patterns.good.md`.
4. Good: configuration properties validation in `examples/practical-patterns.good.md`.
5. Good: negative MockMvc test asserts field error in `examples/practical-patterns.good.md`.
6. Bad: service returns `null` for not found in `examples/review-traps.bad.md`.
7. Bad: controller catches all and returns `200` in `examples/review-traps.bad.md`.
8. Bad: error exposes stack trace or SQL in `examples/review-traps.bad.md`.
9. Bad: DTO lacks nested `@Valid` in `examples/review-traps.bad.md`.
10. Bad: invalid config defaults silently in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- Boundary inputs are validated.
- Domain failures are explicit.
- Error contract is consistent.
- Messages are safe.
- Negative tests assert status and body shape.

# Final response expectations

- Validation and error behavior changed or reviewed.
- Tests/commands run with results.
- Compatibility risks for public error contracts.
