# Purpose

Write integration or Spring slice tests when framework wiring, persistence, serialization, security, or infrastructure behavior matters.

# User input template

```text
Behavior to test:
Layer or infrastructure involved:
Expected success cases:
Expected failure cases:
Existing test style:
Preferred test command:
```

# Required context gathering

- Inspect existing integration, MVC, JPA, security, and Testcontainers patterns.
- Inspect build files and test configuration before adding dependencies or annotations.
- Identify the smallest test scope that proves the behavior.
- Identify MVC, WebFlux, JPA, MongoDB, Cosmos DB, messaging, Azure, cache, or scheduled-job stack before selecting a test style.

# Relevant instructions

- `testing.instructions.md`
- `rest-api-design.instructions.md`
- `data-access.instructions.md`
- `security.instructions.md`
- `reactive-servlet-boundaries.instructions.md`
- `mongodb-cosmosdb.instructions.md`

# Relevant skills

- `spring-test-strategy`
- `api-contract-review`
- `jpa-persistence-review`
- `validation-error-handling`

# Suggested agent

`test-quality`

# Workflow

1. Pick the smallest suitable Spring slice.
2. Use `@WebMvcTest`/MockMvc for MVC, WebTestClient for WebFlux, `@DataJpaTest` for JPA, and Mongo-focused tests for MongoDB.
3. Configure authentication/security test helpers when needed.
4. Seed only data required by the scenario.
5. Keep tests deterministic.

# Testing workflow

- Run the new test class first.
- Run broader tests if shared configuration changed.
- Report skipped external infrastructure if unavailable.
- Avoid `@SpringBootTest` unless full wiring or infrastructure integration is truly required.
- Use Testcontainers only when the project already uses it or realistic infrastructure is required.

# Stop and ask when

- The required infrastructure stack is unclear.
- The test would require a new dependency or container image.
- Security requirements are missing.
- Running the test may mutate real cloud/database resources.
- Validation command cannot be determined safely.

# Self-review checklist

- Test scope is not broader than needed.
- External infrastructure follows existing project practice.
- Negative paths are included.
- Test data is isolated.

# Expected final response

- Integration/slice tests added.
- Scenarios covered.
- Commands run and results.
- Infrastructure assumptions.

# Few-shot examples

## Example request

Add MockMvc tests for invalid create order request.

## Good Copilot behavior

- Use existing controller test style.
- Send invalid JSON and assert `400` plus error shape if established.
- Avoid full context unless required.

## Good final response outline

- Added WebMvc test for validation failure.
- Ran focused controller test.

## Bad Copilot behavior to avoid

- Using a production database.
- Starting the full application when a slice test is enough.
- Ignoring security filters when endpoint security matters.
