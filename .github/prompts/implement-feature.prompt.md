# Purpose

Implement a focused Spring Boot feature across the smallest necessary vertical slice: API, DTOs, service/domain behavior, persistence, validation, error handling, and tests.

# User input template

```text
Feature:
Expected behavior:
Inputs/outputs:
Existing endpoint or class, if known:
Compatibility constraints:
Preferred test command:
```

# Required context gathering

- Inspect existing controllers, DTOs, services, repositories, entities, exceptions, and tests for the feature area.
- Inspect build files and README before running commands.
- Identify target repository conventions before adding new patterns.
- Identify the active stack before editing: MVC, WebFlux, virtual threads, JPA, MongoDB, Cosmos DB, Azure SDK, messaging, cache, scheduled jobs, or another pattern.

# Relevant instructions

- `spring-layering.instructions.md`
- `java-code-style.instructions.md`
- `dependency-injection.instructions.md`
- `rest-api-design.instructions.md`
- `error-handling.instructions.md`
- `testing.instructions.md`
- `config-driven-design.instructions.md`
- `multi-stack-spring.instructions.md`
- `thread-safety.instructions.md`

# Relevant skills

- `spring-boot-feature`
- `spring-test-strategy`
- `validation-error-handling`
- `configuration-management`
- `clean-code-review`

# Suggested agent

`spring-backend-feature`

# Workflow

1. Restate the behavior and any assumptions.
2. Locate the existing vertical slice.
3. Make the smallest code change that preserves existing conventions.
4. Keep controllers thin and put business logic in service/domain code.
5. Prefer config-driven thresholds, toggles, retries, timeouts, and variants over hardcoded values.
6. Avoid hardcoded if/else chains for growing business variants; use strategies or handler maps only when variability is real.
7. Add validation and precise errors where the behavior needs them.
8. Add or update meaningful tests.
9. Review the diff for unrelated changes.

# Testing workflow

- Run focused unit, controller, repository, or integration tests that cover the changed behavior.
- Aim for meaningful coverage above 90% for new or changed code.
- Name new unit tests with `methodName_input_expectation`.
- Avoid `@SpringBootTest` unless Spring wiring/full integration is truly required.
- If the command is unknown, inspect the target repo first.
- Report commands exactly and do not claim tests passed unless they ran.

# Stop and ask when

- Existing stack or execution model is unclear.
- Required behavior conflicts with existing tests or public API.
- A new dependency is needed.
- A new architecture decision is needed.
- Security or authorization requirements are missing.
- Validation command cannot be determined safely.

# Self-review checklist

- No invented dependencies or commands.
- No JPA entities returned from REST APIs.
- Transaction boundaries are in services.
- Tests cover happy path and key negative path.
- Configurable behavior is not hardcoded.
- Spring singleton beans remain stateless.
- Examples and identifiers are generic.

# Expected final response

- Summarize files changed.
- Mention behavior implemented.
- List tests/commands run and results.
- Call out assumptions and risks.

# Few-shot examples

## Example request

Implement `VIP_ORDER` tag when an order total is greater than `5,000,000 VND`.

## Good Copilot behavior

- Inspect existing `Order`, `OrderService`, DTO, and tests.
- Put threshold logic in service/domain code.
- Use `BigDecimal.compareTo`.
- Add boundary tests for exactly `5,000,000` and `5,000,001`.
- Prefer `orders.vip.threshold` and `orders.vip.enabled` configuration if the threshold may vary by environment.

## Good final response outline

- Added VIP tag behavior in `OrderService`.
- Added unit tests named `applyVipTag_totalGreaterThanThreshold_addVipOrderTag`, `applyVipTag_totalEqualToThreshold_doNotAddVipOrderTag`, and a below-threshold case.
- Ran `{{TEST_COMMAND}}`: passed.

## Bad Copilot behavior to avoid

- Using `double` for money.
- Hardcoding an unrelated package path.
- Changing public API fields without being asked.
