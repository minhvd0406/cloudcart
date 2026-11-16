---
applyTo: "**/*{Controller,Resource,Endpoint,Security,Auth,Authentication,Authorization,Filter,Interceptor,Service,Config,Configuration}.java,**/application*.{yml,yaml,properties}"
description: "Authentication, authorization, object access, input validation, secrets, logging, CORS/CSRF, and security test rules."
---

# Purpose

Keep Spring Boot services secure by default without inventing security architecture beyond the target repository.

# Rules

- Authenticate protected endpoints according to the existing Spring Security setup.
- Authorize actions server-side; do not trust client-provided roles, owners, tenant IDs, or user IDs.
- Enforce object-level access control in services or method security when URL rules are not enough.
- Use method security only when the target repo enables or uses it.
- Validate external input at API, messaging, file, and config boundaries.
- Do not log secrets, tokens, passwords, cookies, authorization headers, private keys, or PII.
- Store secrets in environment-specific secure config or secret management, not committed files.
- Configure CORS and CSRF deliberately according to client and authentication style.
- Add negative tests for `401` and `403` when endpoint security changes.

# Good examples

```java
@Service
class OrderAccessService {
    boolean canView(CurrentUser user, Order order) {
        return user.isAdmin() || order.customerId().equals(user.customerId());
    }
}
```

```java
log.info("order_cancellation_failed orderId={} reason={}", orderId, reason);
```

# Bad examples

```java
// Bad: trusts client-provided userId for authorization.
record CancelOrderRequest(Long orderId, Long userId) {
}
```

```java
// Bad: leaks credentials into logs.
log.info("request headers={}", request.getHeader("Authorization"));
```

# Good Copilot behavior

- Inspect existing `SecurityFilterChain`, method security, current-user patterns, and security tests.
- Keep public endpoint changes narrow and explain why an endpoint is public.
- Add tests for unauthenticated and forbidden access when relevant.

# Bad Copilot behavior

- Adding `permitAll` to make a test pass.
- Disabling CSRF/CORS/authentication without context.
- Returning sensitive fields in user or auth responses.

# Checklist

- Authentication expectation is clear.
- Authorization is enforced on the server.
- Object-level access is checked where needed.
- Sensitive data is not logged or returned.
- Security negative tests were added or updated.

# Few-shot mini examples

1. Good: derive current user from the security context, not request body.
2. Good: check `order.customerId()` against current user in service logic.
3. Good: log `orderId` and denial reason without tokens or PII.
4. Good: configure CORS origins from safe configuration when needed.
5. Good: add `401` and `403` tests for changed protected endpoints.
6. Bad: add `permitAll` to make a failing controller test pass.
7. Bad: trust `role` or `tenantId` supplied by the client.
8. Bad: log `Authorization` header or cookies.
9. Bad: disable CSRF/CORS without understanding the auth style.
10. Bad: return password hashes, refresh tokens, or private identifiers in DTOs.
