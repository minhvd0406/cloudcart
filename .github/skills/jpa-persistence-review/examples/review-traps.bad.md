# Bad JPA Persistence Examples

## 1. In-Memory Filtering

```java
return orderRepository.findAll().stream()
        .filter(order -> order.customerId().equals(customerId))
        .toList();
```

## 2. Entity From Controller

```java
@GetMapping("/api/v1/orders/{id}")
Order get(@PathVariable Long id) {
    return orderRepository.findById(id).orElseThrow();
}
```

## 3. Broad EAGER Relationship

```java
@OneToMany(fetch = FetchType.EAGER)
private List<OrderLine> lines;
```

## 4. Unsafe Query Construction

```java
return entityManager.createQuery("from Order o where o.status = '" + status + "'");
```

## 5. Destructive Migration First

```sql
alter table orders drop column legacy_status;
```
