# Good Clean Code Examples

## 1. Guard Clause For Domain Rule

```java
if (order.status() == OrderStatus.SHIPPED) {
    throw new InvalidOrderStateException("Shipped orders cannot be cancelled");
}
```

## 2. Service Keeps Dependency Direction Clear

```java
OrderResponse getOrder(Long id) {
    return orderRepository.findById(id)
            .map(OrderResponse::from)
            .orElseThrow(() -> new OrderNotFoundException(id));
}
```

## 3. Strategy Only For Real Variants

```java
PaymentHandler handler = handlers.stream()
        .filter(candidate -> candidate.supports(method))
        .findFirst()
        .orElseThrow(() -> new UnsupportedPaymentMethodException(method));
```

## 4. Loop Is Clearer Than Stream

```java
for (Order order : orders) {
    if (order.requiresManualReview()) {
        reviewer.enqueue(order.id());
    }
}
```

## 5. Repeated Literal Has A Name

```java
private static final String ORDER_CANCELLED_EVENT = "order.cancelled";
```
