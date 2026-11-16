---
applyTo: "**/*{Controller,Service,Component,Configuration,Config,Repository,Adapter,Client}.java"
description: "Spring dependency injection rules for constructor injection, bean wiring, and dependency boundaries."
---

# Purpose

Keep Spring dependency wiring explicit, testable, and aligned with clean layer boundaries.

# Rules

- Prefer constructor injection for required dependencies.
- Keep injected dependencies in `final` fields when the project style allows it.
- Avoid field injection.
- Avoid service locator patterns such as pulling beans from `ApplicationContext` in business code.
- Controllers should depend on services/use cases, not repositories.
- Use configuration beans intentionally for infrastructure and external clients.
- Avoid circular dependencies; they usually reveal misplaced responsibilities.
- Package-private constructors are acceptable only when they fit repository style and test visibility.
- Do not introduce a new interface for every service unless it supports a real boundary, alternate implementation, or test strategy already used by the repo.

# Good examples

```java
@Service
class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;

    OrderService(OrderRepository orderRepository, PaymentClient paymentClient) {
        this.orderRepository = orderRepository;
        this.paymentClient = paymentClient;
    }
}
```

```java
@RestController
class OrderController {
    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
}
```

# Bad examples

```java
// Bad: field injection hides required dependencies and makes tests harder.
@Service
class OrderService {
    @Autowired
    private OrderRepository orderRepository;
}
```

```java
// Bad: controller skips the service layer and couples HTTP directly to persistence.
@RestController
class OrderController {
    private final OrderRepository orderRepository;
}
```

# Good Copilot behavior

- Inspect existing constructors and component style before adding beans.
- Prefer a simple constructor over annotations that hide dependencies.
- Keep configuration concerns in configuration classes, not scattered through services.

# Bad Copilot behavior

- Adding `@Autowired` fields to match a quick snippet.
- Creating circular dependencies and solving them with lazy injection.
- Injecting repositories into controllers for convenience.

# Checklist

- Required dependencies are visible in constructors.
- No field injection was added.
- Layer dependencies point inward through services/use cases.
- New beans have a clear purpose and tests where wiring matters.

# Few-shot mini examples

1. Good: inject `OrderRepository` through a service constructor.
2. Good: keep dependencies `private final`.
3. Good: inject a list of strategy beans when multiple implementations exist.
4. Good: use Lombok constructor injection only when the repo already uses Lombok.
5. Good: write a slice/integration test when new conditional wiring matters.
6. Bad: use `@Autowired` on mutable fields in new code.
7. Bad: inject repositories directly into controllers.
8. Bad: call `ApplicationContext.getBean()` for ordinary dependencies.
9. Bad: create beans manually with `new` when collaborators need Spring configuration.
10. Bad: add an interface for every service without a second implementation or boundary.
