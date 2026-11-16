# Purpose

Modify existing Spring Boot behavior while preserving API compatibility and repository conventions unless the user explicitly asks for a breaking change.

# User input template

```text
Behavior to modify:
Current behavior:
Desired behavior:
Known files/classes:
Compatibility concerns:
Preferred test command:
```

# Required context gathering

- Inspect current implementation and tests before editing.
- Find callers, public API contracts, DTOs, error mappings, and documentation affected by the change.
- Check whether the behavior is shared across endpoints, jobs, listeners, or services.

# Relevant instructions

- `spring-layering.instructions.md`
- `java-code-style.instructions.md`
- `testing.instructions.md`
- `error-handling.instructions.md`

# Relevant skills

- `spring-boot-feature`
- `spring-test-strategy`
- `clean-code-review`

# Suggested agent

`spring-backend-feature`

# Workflow

1. Identify current behavior and test coverage.
2. Determine the smallest safe change.
3. Update service/domain logic first.
4. Update API, persistence, or configuration only when the behavior requires it.
5. Add regression and boundary tests.
6. Update docs if public behavior changes.

# Testing workflow

- Run tests that covered the old behavior and new regression tests.
- Add a failing regression test first when practical.
- Report skipped broader tests with the reason.

# Self-review checklist

- Existing conventions are preserved.
- Public API changes are intentional.
- Old assumptions are removed from tests.
- No unrelated refactor was included.

# Expected final response

- Describe the behavior change.
- List files changed.
- List tests run and results.
- Note compatibility risks.

# Few-shot examples

## Example request

Change order cancellation so orders with status `PAYMENT_PENDING` can be cancelled, but `SHIPPED` orders still cannot.

## Good Copilot behavior

- Inspect order state transition code and tests.
- Update only the cancellation rule.
- Add tests for `PAYMENT_PENDING`, `SHIPPED`, and an already cancelled order if relevant.

## Good final response outline

- Updated cancellation state validation.
- Added regression tests for allowed and forbidden states.
- Ran focused order service tests.

## Bad Copilot behavior to avoid

- Moving cancellation logic into the controller.
- Renaming statuses or changing persistence mapping without request.
- Removing existing negative tests.
