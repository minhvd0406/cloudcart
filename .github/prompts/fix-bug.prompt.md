# Purpose

Fix a Spring Boot bug with a regression test and minimal behavior-focused code changes.

# User input template

```text
Bug:
Expected behavior:
Actual behavior:
Steps to reproduce:
Known files/classes/logs:
Preferred test command:
```

# Required context gathering

- Inspect the failing path, related tests, and recent conventions.
- Identify the correct layer for the fix.
- Check whether the bug affects API response, service rule, persistence query, transaction, security, or config.
- Identify whether the path is servlet, reactive, scheduled, message-driven, JPA, MongoDB, Cosmos DB, Azure, or cache-related.

# Relevant instructions

- `testing.instructions.md`
- `spring-layering.instructions.md`
- `error-handling.instructions.md`
- `data-access.instructions.md`
- `config-driven-design.instructions.md`
- `unit-test-coverage.instructions.md`

# Relevant skills

- `spring-boot-feature`
- `spring-test-strategy`
- `validation-error-handling`

# Suggested agent

`spring-backend-feature`

# Workflow

1. Reproduce from existing tests or write a focused failing regression test.
2. Fix the root cause in the appropriate layer.
3. Preserve existing stack boundaries and conventions.
4. Move magic thresholds/toggles into configuration when the bug exposes hardcoded behavior.
5. Avoid broad refactors.
6. Run the focused regression test.
7. Check neighboring behavior for accidental regressions.

# Testing workflow

- Add a regression test that would fail before the fix when possible.
- Name new unit tests with `methodName_input_expectation`.
- Aim for meaningful coverage above 90% for the changed code.
- Run the narrowest command covering that test.
- Run broader tests if the fix touches shared behavior.

# Stop and ask when

- The reproduction path conflicts with existing tests or API documentation.
- The fix requires a new dependency or new architecture decision.
- Security/authorization expectations are missing.
- The stack boundary is unclear, such as blocking vs reactive behavior.
- Validation command cannot be determined safely.

# Self-review checklist

- The test proves the user-visible bug.
- The fix is at the root cause.
- Error handling and status codes remain consistent.
- No invented libraries or commands.
- No meaningless coverage padding.

# Expected final response

- Bug fixed.
- Regression test added or updated.
- Commands run and results.
- Any remaining risk.

# Few-shot examples

## Example request

Fix bug: shipped orders can still be cancelled.

## Good Copilot behavior

- Add a service/domain test proving `SHIPPED` cancellation throws a conflict or invalid-state error.
- Name it `cancelOrder_shippedOrder_throwInvalidOrderStateException` or match the repository's closest equivalent.
- Put the check in cancellation business logic.
- Keep the controller delegated to service.

## Good final response outline

- Prevented cancellation for shipped orders.
- Added regression test for `SHIPPED` state.
- Ran order service tests.

## Bad Copilot behavior to avoid

- Only changing frontend validation.
- Returning `null` or `false` instead of a clear domain error.
- Mapping all failures to `500`.
