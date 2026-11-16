# Good Spring Boot Feature Examples

## 1. Thin Controller Delegates

```java
@PostMapping("/api/v1/orders")
@ResponseStatus(HttpStatus.CREATED)
OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
    return orderService.createOrder(request);
}
```

## 2. Service Owns Transaction

```java
@Transactional
OrderResponse cancelOrder(Long id) {
    Order order = orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));
    order.cancel();
    return OrderResponse.from(order);
}
```

## 3. Typed Configuration Drives Behavior

```java
@ConfigurationProperties(prefix = "orders.vip")
record VipOrderProperties(@NotNull BigDecimal threshold, boolean enabled) {
}
```

## 4. Strategy Selection For Real Variants

```java
OrderProcessor processorFor(OrderType type) {
    return processors.stream()
            .filter(processor -> processor.supports(type))
            .findFirst()
            .orElseThrow(() -> new UnsupportedOrderTypeException(type));
}
```

## 5. Boundary Test Names The Behavior

```java
@Test
void applyVipTag_totalEqualToThreshold_doNotAddVipOrderTag() {
    assertThat(service.applyVipTag(new BigDecimal("5000000")))
            .doesNotContain(OrderTag.VIP_ORDER);
}
```
