# Good Validation And Error Examples

## 1. Nested Request Validation

```java
record CreateOrderRequest(@NotEmpty List<@Valid OrderLineRequest> lines) {
}
```

## 2. Domain Conflict

```java
if (order.status() == OrderStatus.SHIPPED) {
    throw new InvalidOrderStateException("Shipped orders cannot be cancelled");
}
```

## 3. Central Error Mapping

```java
@ExceptionHandler(InvalidOrderStateException.class)
ProblemDetail handleConflict(InvalidOrderStateException exception) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
}
```

## 4. Validated Configuration

```java
@ConfigurationProperties(prefix = "orders")
record OrderProperties(@NotNull Duration cancellationWindow) {
}
```

## 5. Field Error Test

```java
mockMvc.perform(post("/api/v1/orders").contentType(APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.lines").exists());
```
