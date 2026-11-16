# Spring Boot Feature Checklist

- Inspect nearby controllers, DTOs, services, repositories, mappers, exception handlers, configuration, and tests before editing.
- Preserve package, naming, mapping, error, validation, and test conventions.
- Keep controllers focused on HTTP binding, validation triggers, status codes, and delegation.
- Keep business decisions, authorization that needs domain data, and transactions in service/domain code.
- Keep repository calls out of controllers.
- Return DTOs, not JPA entities or document models.
- Add validation for request bodies, path variables, query parameters, and configuration properties when relevant.
- Use explicit domain exceptions or result types for expected failures.
- Use typed configuration for thresholds, toggles, retries, timeouts, resource names, and strategy selection.
- Keep Spring singleton beans stateless and avoid request/user fields.
- Use pagination, `Slice`, projections, indexes, or continuation tokens for growing data.
- Add focused unit, slice, repository, integration, or regression tests at the right layer.
- Cover happy path, validation failure, not found, conflict, authorization, and boundary cases when relevant.
- Run the smallest useful test command and report it exactly.
