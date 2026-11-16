# Good JPA Persistence Examples

## 1. Pageable Query

```java
Page<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus status, Pageable pageable);
```

## 2. Projection For List Endpoint

```java
interface OrderSummary {
    Long getId();
    OrderStatus getStatus();
    BigDecimal getTotal();
}
```

## 3. Fetch Required Association

```java
@EntityGraph(attributePaths = "lines")
Optional<Order> findWithLinesById(Long id);
```

## 4. Optimistic Lock

```java
@Version
private long version;
```

## 5. Repository Test Flushes And Clears

```java
entityManager.flush();
entityManager.clear();

assertThat(repository.findByCustomerId(customerId, PageRequest.of(0, 20))).hasSize(1);
```
