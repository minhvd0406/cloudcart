---
applyTo: "**/*.java"
description: "Java streams and lambda rules for readability, side effects, collection transformations, and parallel stream caution."
---

# Purpose

Use streams and lambdas when they improve clarity, and avoid them when they hide side effects or cost.

# Rules

- Use streams for simple transformations, filtering, grouping, and mapping.
- Prefer loops when branching, exceptions, logging, mutation, or debugging would be clearer.
- Avoid side effects inside `map`, `filter`, and `peek`.
- Avoid `peek` except for rare debugging that should not remain in production code.
- Do not use `parallelStream()` without measuring and confirming thread-safety and context requirements.
- Avoid database or remote calls inside stream pipelines.
- Name lambda parameters clearly when intent is not obvious.

# Good examples

```java
List<OrderSummary> summaries = orders.stream()
        .map(OrderSummary::from)
        .toList();
```

```java
for (Order order : orders) {
    if (order.status() == OrderStatus.SHIPPED) {
        shippedCount++;
        log.debug("shipped order found orderId={}", order.id());
    }
}
```

# Bad examples

```java
// Bad: side effects and persistence calls are hidden inside a stream.
orders.stream()
        .peek(order -> order.setStatus(OrderStatus.CANCELLED))
        .forEach(orderRepository::save);
```

# Good Copilot behavior

- Choose the more readable form for the specific code.
- Keep stream pipelines short and side-effect free.
- Avoid parallel streams in request-handling code unless already proven safe.

# Bad Copilot behavior

- Converting every loop to a stream.
- Hiding exceptions, logging, mutation, or remote calls inside lambdas.
- Using parallel streams to fix database query performance.

# Checklist

- Pipeline is readable.
- No hidden side effects.
- No remote or database calls inside transformations.
- Parallelism is justified and safe if used.

# Few-shot mini examples

1. Good: use `orders.stream().map(OrderSummary::from).toList()` for simple mapping.
2. Good: use a loop when logging and branching are central to the logic.
3. Good: use `Collectors.groupingBy(Order::status)` for clear grouping.
4. Good: name lambda parameters when the type is not obvious.
5. Good: keep stream pipelines short enough to scan.
6. Bad: use `peek` to mutate order state.
7. Bad: call repositories from inside `map`.
8. Bad: use `parallelStream()` in request handling without measurement and context checks.
9. Bad: catch and hide exceptions inside lambdas.
10. Bad: convert every loop to a stream for style alone.
