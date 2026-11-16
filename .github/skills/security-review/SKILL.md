---
name: security-review
description: Use when reviewing Spring Boot authentication, authorization, object-level access, sensitive data exposure, secrets, CORS/CSRF, logging, input validation, and security tests.
---

# Skill

Spring Boot security review playbook for application-level security risks.

# When to use

- Reviewing security-sensitive controller, service, config, DTO, logging, messaging, or documentation changes.
- Adding or checking authentication, authorization, object-level access, CORS, CSRF, or method security.
- Checking secret handling, sensitive data exposure, or security regression tests.

# When not to use

- Pure formatting changes.
- Security architecture replacement without enough context.
- Infrastructure-only security reviews that require cloud-console evidence not present in the repo.

# Required context to inspect

- Security configuration, controller mappings, method security, service authorization logic, current-user model, DTOs, logs, config files, and tests.
- Existing object ownership, tenant, role, and permission patterns.
- API error handling, documentation, and observability changes that may expose sensitive data.
- Messaging, scheduled job, and integration handlers when they process external input.

# Step-by-step workflow

1. Identify actors, resources, operations, trust boundaries, and sensitive fields.
2. Check authentication expectations for each endpoint or handler.
3. Check authorization and object-level access in service/domain code.
4. Check that client-provided owner, role, tenant, or user fields are not trusted.
5. Check secrets, tokens, headers, credentials, PII, and payloads in config, logs, responses, and docs.
6. Check CORS/CSRF behavior against the authentication style and project convention.
7. Check input validation at API, messaging, file, and configuration boundaries.
8. Check `401`, `403`, and ownership tests.
9. Rank findings by severity and exploitability.

# Java best practices

- Keep authorization checks readable, centralized enough to audit, and easy to test.
- Avoid logging full objects that may include sensitive fields.
- Prefer explicit allow/deny decisions over boolean expressions scattered across controllers.
- Avoid static mutable security context caches.

# Spring Boot best practices

- Keep endpoint access rules narrow.
- Use method security only when the project enables and uses it.
- Enforce object-level authorization where URL-level rules are insufficient.
- Configure CORS and CSRF deliberately; do not disable them to make tests pass.
- Keep secrets in environment-specific secure configuration or secret managers, not committed files.

# Multi-stack adaptation notes

- MVC/WebFlux: ensure security filters are active in tests that claim endpoint security coverage.
- Method security: check annotations and service tests for role and ownership behavior.
- JPA/Mongo/Cosmos: enforce ownership using server-side current-user or tenant context, not request fields.
- Messaging: validate event payloads and design idempotency before side effects.
- Azure/cloud integrations: never log connection strings, SAS tokens, access keys, or full sensitive payloads.

# Thread-safety rules

- Do not store current user, tenant, role, JWT claims, request headers, or authorization decisions in singleton fields.
- Use framework security context access patterns already present in the repo.
- Treat caches of permissions or tokens as security-sensitive and require expiration/invalidation.

# Configuration-driven design rules

- Keep CORS origins, public paths, token issuer/audience, security toggles, and client IDs in typed configuration where project conventions allow.
- Validate security configuration and fail fast when required values are absent.
- Do not hardcode environment IDs, tenant IDs, internal domains, or credentials.

# Testing requirements

- Negative tests for unauthenticated (`401`) and forbidden (`403`) access when endpoint security changes.
- Ownership tests for resource-specific endpoints.
- Tests that sensitive fields are excluded from responses and logs when relevant.
- Validation tests for external input.
- Security config tests should preserve existing filters rather than bypass them silently.

# Few-shot examples

1. Good: object-level access service in `examples/practical-patterns.good.md`.
2. Good: safe structured security log in `examples/practical-patterns.good.md`.
3. Good: current user comes from server context in `examples/practical-patterns.good.md`.
4. Good: narrow `SecurityFilterChain` rule in `examples/practical-patterns.good.md`.
5. Good: `403` ownership test in `examples/practical-patterns.good.md`.
6. Bad: trusts client-provided user ID in `examples/review-traps.bad.md`.
7. Bad: `permitAll` added to protected endpoint in `examples/review-traps.bad.md`.
8. Bad: logs authorization header in `examples/review-traps.bad.md`.
9. Bad: response exposes token or PII in `examples/review-traps.bad.md`.
10. Bad: CORS allows all origins with credentials in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- Authentication expectations are explicit.
- Authorization is enforced server-side.
- Object ownership and tenant isolation are checked where needed.
- Sensitive data is not logged, returned, or committed.
- CORS/CSRF choices are deliberate.
- Input validation exists at external boundaries.
- Security negative tests exist or are requested.

# Final response expectations

- Findings ordered by severity.
- Missing tests and sensitive-data risks.
- Open questions about auth assumptions.
- Clear separation between confirmed findings and assumptions.
