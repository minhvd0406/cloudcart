# Bad API Contract Examples

## 1. Entity Crosses API Boundary

```java
@PostMapping("/api/v1/orders")
Order create(@RequestBody Order order) {
    return orderRepository.save(order);
}
```

## 2. Unbounded List Endpoint

```java
@GetMapping("/api/v1/orders")
List<OrderResponse> allOrders() {
    return orderService.findAll();
}
```

## 3. Client Controls Server Fields

```java
record CreateOrderRequest(Long id, Long ownerId, OrderStatus status, Instant createdAt) {
}
```

## 4. All Errors Become String 500s

```java
@ExceptionHandler(Exception.class)
String handle(Exception exception) {
    return exception.toString();
}
```

## 5. Inconsistent Versioning

```java
@GetMapping("/v3/order-management/orders")
List<OrderResponse> orders() {
    return service.findRecent();
}
```
