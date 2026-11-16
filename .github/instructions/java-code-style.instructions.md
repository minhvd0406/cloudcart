---
applyTo: "**/*.java"
description: "Java 17/21-friendly code style, readability, naming, immutability, and numeric comparison rules."
---

# Purpose

Help Copilot produce Java code that is clear, maintainable, compatible with the target repository, and not over-engineered.

# Rules

- Write Java 17-compatible code unless the target project clearly supports Java 21 features.
- Use Java 21 features only when they improve clarity and the project already targets Java 21.
- Choose meaningful names that describe domain intent.
- Prefer small cohesive methods and simple guard clauses.
- Prefer immutability for DTOs, value objects, and dependencies.
- Prefer constructor injection for Spring components.
- Avoid static helpers unless the behavior is pure, stable, and does not belong to a domain type or Spring bean.
- Avoid premature abstractions, inheritance hierarchies, and generic frameworks.
- Use streams when they are readable; use loops when they are clearer.
- Use `Optional` mainly as a return type, not for fields, parameters, or serialization models.
- Use `BigDecimal.compareTo` for business numeric equality or thresholds.
- Do not use `double` or `float` for money.
- Check nullability deliberately at boundaries and avoid accidental `NullPointerException` paths.
- Avoid magic strings and magic numbers; extract repeated literals into constants or configuration.
- Put shared log event names and repeated log templates in constants when reused.
- Use structured logging placeholders instead of string concatenation.
- Use `String.formatted(...)` for non-log formatted strings when it reads better than concatenation.

# Good examples

```java
void validateCancelable(Order order) {
    if (order.status() == OrderStatus.SHIPPED) {
        throw new InvalidOrderStateException("Shipped orders cannot be cancelled");
    }
}
```

```java
boolean reachesVipThreshold(BigDecimal total) {
    return total.compareTo(new BigDecimal("5000000")) > 0;
}
```

```java
private static final String ORDER_CANCELLED_EVENT = "order_cancelled";

void logCancellation(Long orderId) {
    log.info("{} orderId={}", ORDER_CANCELLED_EVENT, orderId);
}
```

# Bad examples

```java
// Bad: deeply nested and harder to scan.
void cancel(Order order) {
    if (order != null) {
        if (order.status() != OrderStatus.SHIPPED) {
            order.cancel();
        }
    }
}
```

```java
// Bad: BigDecimal.equals also compares scale, so 5.0 and 5.00 differ.
boolean isExactAmount(BigDecimal amount) {
    return amount.equals(new BigDecimal("5000000"));
}
```

```java
// Bad: repeated magic string and concatenated log message.
log.info("order_cancelled orderId=" + orderId);
```

# Good Copilot behavior

- Match the repository formatter, naming, nullability, and test style.
- Prefer explicit code over clever fluent chains when behavior is important.
- Keep examples generic and package names reusable.

# Bad Copilot behavior

- Introducing utility classes before duplication or complexity justifies them.
- Rewriting readable loops into complex streams.
- Adding Lombok, MapStruct, or other tools unless the repository already uses them or the user asks.
- Adding hardcoded if/else chains for growing business variants when configuration or strategy selection is needed.

# Checklist

- Names reveal intent.
- Control flow is easy to scan.
- Money uses `BigDecimal`.
- `Optional` is not used in fields or DTO properties.
- New abstractions remove real complexity.
- Repeated literals are constants or configuration.

# Few-shot mini examples

1. Good: name a method `cancelShippedOrder` only if it truly handles shipped orders.
2. Good: compare money with `amount.compareTo(threshold) > 0`.
3. Good: use `record OrderResponse(Long id, OrderStatus status)` when DTOs are immutable and repo style allows records.
4. Good: extract repeated event name `ORDER_CANCELLED_EVENT`.
5. Good: use guard clauses to keep state checks readable.
6. Bad: compare `BigDecimal` business values with `equals`.
7. Bad: use `double` for prices, totals, or thresholds.
8. Bad: add `Optional<String>` as a DTO field.
9. Bad: introduce `CommonUtils` for domain-specific order rules.
10. Bad: rewrite readable branch logic into a dense stream chain.
