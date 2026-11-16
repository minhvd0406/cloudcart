---
name: api-contract-review
description: Use when reviewing or designing Spring REST API contracts, DTOs, validation, status codes, pagination, versioning, authorization expectations, and error responses.
---

# Skill

REST API contract review playbook for Spring Boot services.

# When to use

- Reviewing controller, request DTO, response DTO, validation, and error handling changes.
- Adding or changing REST endpoints or API documentation.
- Checking pagination, status codes, versioning, authorization expectations, or backward compatibility.
- Reviewing generated or hand-written API examples.

# When not to use

- Pure persistence-only changes with no API surface.
- Internal service refactors with no contract impact.
- Broad API redesign without a concrete endpoint or compatibility goal.

# Required context to inspect

- Existing controllers, DTOs, routes, API versioning, exception handlers, validation patterns, controller tests, and API docs.
- Security configuration, current-user model, and object-access service when endpoint access changes.
- Existing pagination, sorting, filtering, and error response conventions.
- README or changelog when public behavior changes.

# Step-by-step workflow

1. Identify the public request, response, status-code, and error contract.
2. Check DTO safety: no persistence model exposure and no server-managed fields accepted from clients.
3. Check validation on body, path variables, query parameters, headers, and nested DTOs.
4. Check precise status codes for create, update, delete, validation, not-found, conflict, and authorization behavior.
5. Check pagination, sorting bounds, and maximum sizes for collection endpoints.
6. Check versioning and backward compatibility against existing conventions.
7. Check authorization and object ownership for protected resources.
8. Identify missing contract, validation, and security tests.
9. Rank findings by severity and user impact.

# Java best practices

- Use immutable request/response DTOs when compatible with the project.
- Avoid raw `Map<String, Object>` or untyped request bodies for stable contracts.
- Keep mapping explicit when fields are server-managed, sensitive, or renamed.
- Prefer clear enum handling and validation for constrained values.

# Spring Boot best practices

- Use `@Valid` for request bodies and nested validation.
- Add constraint annotations to path/query arguments when constraints matter.
- Return DTOs, not entities or document models.
- Use `ResponseEntity` only when status, headers, or conditional behavior require it.
- Use `ProblemDetail` or the repository's established error shape.
- Preserve existing API versioning, route naming, and page response style.

# Multi-stack adaptation notes

- MVC: use MockMvc or `@WebMvcTest` for contract tests.
- WebFlux: use WebTestClient and avoid blocking contract logic.
- JPA/Mongo/Cosmos: API responses should map from read models or DTOs, not persistence models.
- Messaging-triggered APIs: document async/idempotent behavior when exposed through command endpoints.
- Security stacks vary; review the actual `SecurityFilterChain`, method security, and test helpers.

# Thread-safety rules

- Controllers must not store request, body, principal, locale, tenant, or pagination state in fields.
- Mappers used as singleton beans must be stateless or use thread-safe collaborators.
- Do not cache per-user authorization decisions in mutable fields.

# Configuration-driven design rules

- Put max page size, default page size, feature toggles, allowed origins, and external API URLs in configuration when they may vary.
- Validate configured API bounds.
- Do not hardcode environment-specific version paths, tenant IDs, or public host names.

# Testing requirements

- Contract tests should assert response shape, not only status.
- Cover success, validation failure, not-found, conflict, pagination bounds, and authorization where relevant.
- Include `401` and `403` negative tests when endpoint security changes.
- Add compatibility tests or documentation notes for breaking changes.

# Few-shot examples

1. Good: DTO create endpoint with `201` in `examples/practical-patterns.good.md`.
2. Good: nested Bean Validation in `examples/practical-patterns.good.md`.
3. Good: bounded pageable endpoint in `examples/practical-patterns.good.md`.
4. Good: `ProblemDetail` not-found mapping in `examples/practical-patterns.good.md`.
5. Good: MockMvc contract assertion in `examples/practical-patterns.good.md`.
6. Bad: entity accepted and returned at API boundary in `examples/review-traps.bad.md`.
7. Bad: unbounded list endpoint in `examples/review-traps.bad.md`.
8. Bad: client-provided owner or status in create request in `examples/review-traps.bad.md`.
9. Bad: all exceptions mapped to `500` string bodies in `examples/review-traps.bad.md`.
10. Bad: route version invented beside existing convention in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- DTOs protect the API boundary.
- Validation is complete and nested where needed.
- Status codes are precise.
- Errors follow project convention.
- Pagination or documented bounds exist for growing collections.
- Authorization and object ownership are enforced server-side.
- Contract tests cover response shape and negative paths.

# Final response expectations

- Findings ordered by severity for reviews.
- Contract changes, compatibility notes, and migration risk for implementations.
- Missing tests or docs called out explicitly.
- Commands run or validation skipped with reasons.
