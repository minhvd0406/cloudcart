# Purpose

Write focused unit tests for Java and Spring Boot business logic without unnecessary Spring context startup.

# User input template

```text
Class or behavior to test:
Important scenarios:
Known edge cases:
Existing test style:
Preferred test command:
```

# Required context gathering

- Inspect the class under test and nearby tests.
- Identify observable behavior, domain errors, boundaries, and dependencies.
- Check assertion and mocking libraries already used.
- Check coverage expectations or reports when present.

# Relevant instructions

- `testing.instructions.md`
- `unit-test-coverage.instructions.md`
- `java-code-style.instructions.md`

# Relevant skills

- `spring-test-strategy`
- `clean-code-review`

# Suggested agent

`test-quality`

# Workflow

1. Name new unit tests with `methodName_input_expectation`.
2. Use Arrange-Act-Assert or Given-When-Then.
3. Mock only true collaborators.
4. Do not mock value objects, DTOs, or the system under test.
5. Cover success, failure, and boundary cases.
6. Aim for meaningful coverage above 90% for new or changed code without padding.

# Testing workflow

- Run the test class or focused method if the build tool supports it.
- Report the exact command and result.
- Avoid `@SpringBootTest` for pure logic.

# Stop and ask when

- The class behavior is unclear or conflicts with existing tests.
- Required collaborators cannot be constructed without a new design decision.
- A new test dependency is needed.
- The validation command cannot be determined safely.

# Self-review checklist

- Tests fail for the right reason if behavior is removed.
- No test only verifies a mock interaction.
- Boundary values are covered.
- Fixtures are readable.
- Coverage is meaningful, not line padding.

# Expected final response

- Tests added.
- Behavior covered.
- Commands run and results.
- Any untested risk.

# Few-shot examples

## Example request

Add unit tests for `VIP_ORDER` when order total is greater than `5,000,000 VND`.

## Good Copilot behavior

- Test below, equal, and above threshold.
- Use names such as `applyVipTag_totalGreaterThanThreshold_addVipOrderTag`.
- Use `BigDecimal` and `compareTo`-compatible assertions.
- Avoid Spring context.

## Good final response outline

- Added three unit tests for VIP threshold.
- Ran focused unit test command.

## Bad Copilot behavior to avoid

- Testing only `repository.save` was called.
- Using floating point money.
- Starting `@SpringBootTest` for pure logic.
