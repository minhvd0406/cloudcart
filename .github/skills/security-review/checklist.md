# Security Review Checklist

- Identify actors, resources, operations, trust boundaries, and sensitive fields.
- Inspect `SecurityFilterChain`, method security, current-user access, controller routes, services, DTOs, logs, config, and tests.
- Confirm protected endpoints require authentication.
- Confirm authorization happens on the server.
- Confirm object-level access is enforced for resource-specific operations.
- Confirm client-provided role, owner, user, or tenant fields are not trusted.
- Confirm secrets, tokens, credentials, cookies, authorization headers, and PII are not logged or returned.
- Confirm CORS/CSRF settings match the authentication style and project convention.
- Confirm external input is validated.
- Confirm `401`, `403`, and ownership tests exist when security behavior changes.
- Confirm docs and examples do not include secret-shaped values.
