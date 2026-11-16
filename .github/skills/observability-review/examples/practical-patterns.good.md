# Good Observability Examples

## 1. Safe Structured Log

```java
log.info("order_cancelled orderId={} outcome={}", orderId, "success");
```

## 2. Low-Cardinality Metric

```java
counter.increment("operation", "cancel_order", "outcome", "conflict");
```

## 3. Message Retry Outcome

```java
log.warn("order_event_retry messageId={} eventType={} attempt={}", messageId, eventType, attempt);
```

## 4. Narrow Actuator Exposure

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
```

## 5. Correlation Propagation

```java
headers.set("X-Correlation-Id", correlationId);
```
