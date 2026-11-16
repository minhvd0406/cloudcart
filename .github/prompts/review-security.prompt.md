# Purpose

Review Spring Boot changes for authentication, authorization, object-level access, sensitive data exposure, secret handling, and security tests.

# User input template

```text
Security-sensitive change:
Diff/files:
Auth model:
Known roles/permissions:
Tests run by author:
```

# Required context gathering

- Inspect security configuration, controllers, services, current-user access, DTOs, logs, and tests.
- Check whether object ownership and role checks happen server-side.
- Look for secrets in code, config, logs, and docs.
- Check Azure, messaging, stream, and external SDK changes for secret/resource-name hardcoding and sensitive payload logging.

# Relevant instructions

- `security.instructions.md`
- `rest-api-design.instructions.md`
- `observability.instructions.md`
- `testing.instructions.md`
- `azure-integration.instructions.md`

# Relevant skills

- `security-review`
- `api-contract-review`
- `spring-test-strategy`
- `observability-review`

# Suggested agent

`security-reviewer`

# Workflow

1. Identify protected resources and actors.
2. Check authentication and authorization enforcement.
3. Check DTOs and logs for sensitive data.
4. Check CORS/CSRF changes if present.
5. Check message handlers and cloud integrations for idempotency, retry/DLQ behavior, and safe logging.
6. Verify negative tests for `401` and `403`.

# Testing workflow

- Look for unauthenticated, forbidden, and ownership tests.
- Recommend focused tests for missing security paths.
- Check duplicate-message/idempotency tests when reviewing message handlers.

# Stop and ask when

- Authorization/ownership rules are missing.
- Endpoint exposure is unclear.
- Cloud resource, retry, or DLQ semantics are unknown.
- A new security dependency or architecture decision is needed.

# Self-review checklist

- Object-level access is not trusted to clients.
- Secrets and PII are not exposed.
- Security config changes are narrow.
- Findings are ranked by severity.

# Expected final response

- Security findings first.
- Test gaps.
- Open questions about auth assumptions.
- Brief summary.

# Few-shot examples

## Example request

Review security-sensitive user/order access change.

## Good Copilot behavior

- Check that service verifies the authenticated user can access the order.
- Flag request-body `userId` trust.
- Require `401` and `403` tests.

## Good final response outline

- High: endpoint trusts client-provided `userId`.
- Medium: missing forbidden test.
- Risk: ownership model unclear for admin users.

## Bad Copilot behavior to avoid

- Focusing only on input validation.
- Suggesting broad `permitAll`.
- Ignoring logs and response DTOs.
