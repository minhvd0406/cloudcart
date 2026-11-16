# Bad Spring Boot Feature Examples

## 1. Controller Owns Business And Persistence

```java
@DeleteMapping("/api/v1/orders/{id}")
Order cancel(@PathVariable Long id) {
    Order order = orderRepository.findById(id).orElseThrow();
    order.setStatus(OrderStatus.CANCELLED);
    return orderRepository.save(order);
}
```

## 2. Request DTO Accepts Server-Managed Fields

```java
record CreateOrderRequest(Long id, Long customerId, OrderStatus status, Instant createdAt) {
}
```

## 3. Singleton Stores Request State

```java
@Service
class OrderService {
    private Long currentCustomerId;
}
```

## 4. Environment Detail Hardcoded In Service

```java
void publish(OrderEvent event) {
    serviceBusClient.send("orders-prod", event, Duration.ofSeconds(30));
}
```

## 5. Full Context For Pure Logic

```java
@SpringBootTest
class OrderTagServiceTest {
    @Test
    void testVip() {
        assertThat(service.applyVipTag(new BigDecimal("5000001"))).isNotEmpty();
    }
}
```
