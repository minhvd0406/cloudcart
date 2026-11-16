# Bad Observability Examples

## 1. Full Payload Log

```java
log.info("request={}", request);
```

## 2. High-Cardinality Metric Tag

```java
counter.increment("userEmail", user.email());
```

## 3. String Concatenation In Logging

```java
log.debug("response=" + expensiveSerializer.serialize(response));
```

## 4. Overexposed Actuator

```yaml
management.endpoints.web.exposure.include: "*"
```

## 5. Lost Context In Async

```java
CompletableFuture.runAsync(() -> orderService.cancel(orderId));
```
