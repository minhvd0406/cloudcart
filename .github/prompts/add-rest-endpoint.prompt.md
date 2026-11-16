# Purpose

Add or modify a Spring REST endpoint with safe DTOs, validation, status codes, error handling, and tests.

# User input template

```text
Endpoint:
Method and path:
Request body/query/path parameters:
Response shape:
Auth/authorization requirements:
Error cases:
Preferred test command:
```

# Required context gathering

- Inspect existing controller naming, base paths, API versioning, DTO records/classes, validation, error handlers, and controller tests.
- Inspect service methods before creating new ones.
- Check security configuration when endpoint access changes.
- Identify MVC vs WebFlux before choosing MockMvc, WebTestClient, synchronous DTOs, `Mono`, or `Flux`.

# Relevant instructions

- `rest-api-design.instructions.md`
- `validation.instructions.md`
- `security.instructions.md`
- `testing.instructions.md`
- `reactive-servlet-boundaries.instructions.md`
- `multi-stack-spring.instructions.md`

# Relevant skills

- `spring-boot-feature`
- `api-contract-review`
- `validation-error-handling`
- `spring-test-strategy`

# Suggested agent

`rest-api-contract`

# Workflow

1. Confirm API contract and compatibility.
2. Add request/response DTOs without server-managed create fields.
3. Add a thin controller method.
4. Delegate to service/domain logic.
5. Add precise error handling if needed.
6. Add MockMvc/WebMvc tests for MVC or WebTestClient/StepVerifier tests for WebFlux.
7. Prefer config-driven behavior for endpoint limits, toggles, and timeouts.

# Testing workflow

- Test success status and response shape.
- Test invalid request returns `400`.
- Test `404`, `409`, `401`, or `403` when relevant.
- Name new unit tests with `methodName_input_expectation`.
- Avoid full `@SpringBootTest` unless the endpoint behavior requires full wiring.

# Stop and ask when

- Existing API versioning or MVC/WebFlux stack is unclear.
- Authorization requirements are missing.
- The endpoint conflicts with an existing route or public contract.
- A new dependency or new API-wide convention is needed.
- Validation command cannot be determined safely.

# Self-review checklist

- DTOs, not entities, cross the REST boundary.
- Inputs are validated.
- Collection responses are paginated or bounded.
- Security expectations are tested.

# Expected final response

- Endpoint added.
- Files changed.
- Tests run and results.
- Contract assumptions.

# Few-shot examples

## Example request

Add `GET /api/v1/orders/{id}/summary` endpoint.

## Good Copilot behavior

- Inspect existing order controller and DTO conventions.
- Add `OrderSummaryResponse`.
- Delegate to `OrderService.getSummary(id)`.
- Add tests for `200`, `404`, and forbidden access if applicable.

## Good final response outline

- Added summary endpoint returning DTO.
- Covered success and not-found cases.
- Ran focused controller and service tests.

## Bad Copilot behavior to avoid

- Returning `Order` entity directly.
- Adding an unversioned path beside versioned APIs.
- Ignoring existing error contract.
