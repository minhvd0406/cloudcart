---
applyTo: "**/*{Service,UseCase,Facade,Repository,Controller}.java"
description: "Spring transaction boundary, read-only transaction, state transition, and idempotency rules."
---

# Purpose

Keep data changes consistent by placing transaction boundaries where business use cases are orchestrated.

# Rules

- Put transaction boundaries at the service/use-case layer.
- Use `@Transactional(readOnly = true)` for read-only service methods when the project uses transactions for reads.
- Avoid transactions in controllers.
- Avoid long transactions around remote calls, file I/O, sleeps, or user interaction.
- Keep state transitions consistent within one use case.
- Consider idempotency for retries, external callbacks, commands, and cancellation flows.
- Use optimistic locking when concurrent updates can conflict.
- Do not assume repository `save` is always required for managed JPA entities; follow project style and clarity.

# Good examples

```java
@Service
class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    OrderResponse cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.cancel();
        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    OrderResponse getOrder(Long id) {
        return orderRepository.findById(id)
                .map(OrderResponse::from)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}
```

# Bad examples

```java
// Bad: controller owns transaction and business mutation.
@Transactional
@DeleteMapping("/orders/{id}")
OrderResponse cancel(@PathVariable Long id) {
    Order order = orderRepository.findById(id).orElseThrow();
    order.cancel();
    return OrderResponse.from(order);
}
```

```java
// Bad: remote call is unnecessarily wrapped in the database transaction.
@Transactional
void chargeAndSave(Order order) {
    paymentClient.charge(order.total());
    orderRepository.save(order);
}
```

# Good Copilot behavior

- Inspect existing service transaction style before adding annotations.
- Keep remote calls outside transactions unless consistency requirements demand otherwise.
- Add tests for invalid state transitions and retry/idempotency behavior when relevant.

# Bad Copilot behavior

- Slapping `@Transactional` onto controllers or every method.
- Hiding concurrency conflicts with catch-and-ignore logic.
- Splitting one invariant across multiple independent transactions without reason.

# Checklist

- Transaction boundary is in the service/use case.
- Reads are marked read-only when appropriate.
- Remote work is not inside a long transaction by accident.
- State transitions are atomic.
- Concurrency/idempotency risks were considered.

# Few-shot mini examples

1. Good: put `@Transactional` on `OrderService.cancelOrder`.
2. Good: use `@Transactional(readOnly = true)` for service read methods when project style supports it.
3. Good: check state and mutate aggregate inside one transaction.
4. Good: use optimistic locking for concurrent order updates.
5. Good: make retried command processing idempotent.
6. Bad: annotate controllers as transactional.
7. Bad: call a payment API inside a long database transaction.
8. Bad: split one state transition across unrelated transactions.
9. Bad: assume `save` is required or not required without following project style.
10. Bad: ignore duplicate external callbacks.
