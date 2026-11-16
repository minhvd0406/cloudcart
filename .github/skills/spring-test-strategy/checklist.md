# Spring Test Strategy Checklist

- Identify the behavior and the smallest layer that can prove it.
- Prefer pure unit tests for domain/service logic.
- Use MVC/WebFlux slice tests for API behavior.
- Use repository/data tests for custom queries and mappings.
- Use full integration tests only when wiring or infrastructure matters.
- Cover happy path, validation failure, not found, conflict, authorization, and boundaries where relevant.
- Use deterministic clocks, IDs, and fixtures.
- Avoid sleeps, test order dependencies, and production external resources.
- Avoid tests that only execute lines or assert mock calls with no behavior assertion.
- Name new unit tests with `methodName_input_expectation` when compatible.
- Keep coverage meaningful and above project expectations for changed code.
- Run focused tests first and report exact commands.
