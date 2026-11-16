# Bad Validation And Error Examples

## 1. Null For Not Found

```java
Order find(Long id) {
    return repository.findById(id).orElse(null);
}
```

## 2. Failure Returned As Success

```java
try {
    service.cancel(id);
    return "ok";
} catch (Exception exception) {
    return "failed";
}
```

## 3. Leaks Internals

```java
return ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, exception.toString());
```

## 4. Missing Nested Validation

```java
record CreateOrderRequest(@NotEmpty List<OrderLineRequest> lines) {
}
```

## 5. Silent Invalid Config

```java
Duration timeout = Duration.ofSeconds(0);
```
