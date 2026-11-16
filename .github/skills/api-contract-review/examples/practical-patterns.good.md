# Good API Contract Examples

## 1. Create Endpoint Uses DTO And 201

```java
@PostMapping("/api/v1/orders")
@ResponseStatus(HttpStatus.CREATED)
OrderResponse create(@Valid @RequestBody CreateOrderRequest request) {
    return orderService.createOrder(request);
}
```

## 2. Nested Validation Is Explicit

```java
record CreateOrderRequest(
        @NotNull Long customerId,
        @NotEmpty List<@Valid OrderLineRequest> lines) {
}
```

## 3. Growing Collection Is Bounded

```java
@GetMapping("/api/v1/orders")
Page<OrderSummaryResponse> search(OrderSearchRequest request, Pageable pageable) {
    return orderService.search(request, pageable);
}
```

## 4. Error Contract Is Centralized

```java
@ExceptionHandler(OrderNotFoundException.class)
ProblemDetail handle(OrderNotFoundException exception) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Order not found");
}
```

## 5. Contract Test Asserts Body Shape

```java
mockMvc.perform(post("/api/v1/orders").contentType(APPLICATION_JSON).content(json))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$.status").value("PENDING"));
```
