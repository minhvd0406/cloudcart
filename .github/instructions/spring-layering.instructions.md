---
applyTo: "**/*.java"
description: "Spring Boot layering, DTO boundaries, service responsibilities, transactions, and mapping rules."
---

# Purpose

Keep Spring Boot code organized so HTTP, business logic, persistence, validation, mapping, transactions, and exception handling stay in the right layers.

# Rules

- Controllers handle HTTP binding, request validation triggers, status codes, and delegation.
- DTOs define request and response contracts; they should not contain persistence annotations or domain behavior.
- Services own business rules, use-case orchestration, authorization decisions that need domain data, and transaction boundaries.
- Repositories own persistence access only.
- Entities model persisted state and local domain invariants; avoid making them API response models.
- Exception handlers map expected application exceptions to the project's error response format.
- Mapping belongs in services, dedicated mappers, assemblers, or DTO factory methods according to the target repo convention.
- Put `@Transactional` on service methods, not controllers.
- Do not call repositories from controllers.
- Do not expose JPA entities directly from REST APIs.

# Good examples

```java
package com.example.orders;

@RestController
@RequestMapping("/api/v1/orders")
class OrderController {
    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    OrderResponse getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }
}
```

```java
package com.example.orders;

@Service
class OrderService {
    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    OrderResponse cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.cancel();
        return OrderResponse.from(order);
    }
}
```

# Bad examples

```java
// Bad: controller calls the repository, mutates the entity, owns business logic,
// and returns a persistence entity through the REST boundary.
@RestController
class OrderController {
    private final OrderRepository orderRepository;

    @DeleteMapping("/api/v1/orders/{id}")
    Order cancel(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
```

```java
// Bad: request DTO is mixed with persistence concerns and server-managed fields.
record CreateOrderRequest(
        @Id Long id,
        Long customerId,
        OrderStatus status,
        Instant createdAt) {
}
```

# Good Copilot behavior

- Inspect nearby controllers, services, DTOs, repositories, entities, mappers, exceptions, and tests before editing.
- Preserve existing package conventions and mapping style when they are reasonable.
- Add or update tests at the layer where behavior lives.
- Keep a vertical feature change coherent without unrelated refactors.

# Bad Copilot behavior

- Moving business logic into controllers for speed.
- Creating new mapper libraries or architecture patterns without project evidence.
- Adding broad helper classes that know about many unrelated domains.
- Changing package boundaries without tests and an explicit reason.

# Checklist

- Controller is thin and delegates to a service/use case.
- Request and response DTOs are separate from entities.
- Service owns business decisions and transaction scope.
- Repository access is bounded and not used from controllers.
- Expected exceptions map to clear API errors.
- Tests cover the changed behavior and important negative paths.

# Few-shot mini examples

1. Good: controller maps HTTP request and delegates to `OrderService`.
2. Good: service owns `@Transactional` state transition logic.
3. Good: repository exposes a bounded query such as `findByCustomerId(..., Pageable)`.
4. Good: mapper or DTO factory converts entity to response DTO.
5. Good: exception handler maps domain exceptions to project error shape.
6. Bad: controller calls repository, mutates entity, and returns it.
7. Bad: request DTO contains `@Id`, `createdAt`, or `status` server fields.
8. Bad: repository contains business authorization decisions.
9. Bad: entity imports web annotations to shape JSON.
10. Bad: transaction boundary is placed on controller methods.
