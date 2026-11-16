# Validation And Error Handling Checklist

- Inspect DTOs, controllers, services, exception handlers, tests, and API docs.
- Validate request bodies with `@Valid`.
- Validate nested DTOs and collection elements when needed.
- Validate path/query parameters when constraints matter.
- Validate configuration properties and fail fast.
- Use explicit domain exceptions for expected failures.
- Map errors centrally through existing error contract.
- Keep messages useful but safe.
- Avoid catch-all handlers that hide failures.
- Add tests for invalid request, not found, conflict, malformed input, and configuration failure when relevant.
