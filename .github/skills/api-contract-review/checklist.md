# API Contract Review Checklist

- Inspect existing route style, versioning, DTOs, validation, error handler, controller tests, and security config.
- Confirm controllers do not accept or return persistence entities/documents.
- Confirm create/update requests cannot set server-managed fields such as id, status, owner, role, or audit timestamps.
- Confirm request bodies use `@Valid` and nested DTOs use `@Valid` on collection elements where needed.
- Confirm path variables and query parameters have constraints when constraints matter.
- Confirm response status codes match behavior.
- Confirm collection endpoints are paginated or deliberately bounded.
- Confirm error response shape matches existing `ProblemDetail` or custom contract.
- Confirm endpoint auth and object-level access are covered.
- Confirm MockMvc/WebMvc/WebTestClient tests assert response body shape.
- Confirm API docs or README are updated when public behavior changes.
