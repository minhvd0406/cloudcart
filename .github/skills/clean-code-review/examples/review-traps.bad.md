# Bad Clean Code Examples

## 1. Premature Generic Abstraction

```java
interface GenericDomainProcessor<T, R, C> {
    R process(T target, C context);
}
```

## 2. Controller Does Everything

```java
@PostMapping("/orders/{id}/cancel")
Order cancel(@PathVariable Long id) {
    Order order = repository.findById(id).orElseThrow();
    order.setStatus(CANCELLED);
    return repository.save(order);
}
```

## 3. Static Mutable Cache

```java
static final Map<String, Order> ORDERS = new HashMap<>();
```

## 4. Stream Hides Database Calls

```java
ids.stream()
        .map(orderRepository::findById)
        .flatMap(Optional::stream)
        .toList();
```

## 5. Optional Field In DTO

```java
record OrderResponse(Long id, Optional<String> cancellationReason) {
}
```
