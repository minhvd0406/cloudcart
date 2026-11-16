# Good Security Review Examples

## 1. Object-Level Access Service

```java
boolean canView(CurrentUser user, Order order) {
    return user.isAdmin() || order.customerId().equals(user.customerId());
}
```

## 2. Safe Structured Log

```java
log.warn("order_access_denied orderId={} principalId={}", orderId, currentUser.id());
```

## 3. Server-Side User Context

```java
OrderResponse getOrder(Long id, CurrentUser currentUser) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    accessService.requireCanView(currentUser, order);
    return OrderResponse.from(order);
}
```

## 4. Narrow Endpoint Rule

```java
authorize.requestMatchers(HttpMethod.GET, "/api/v1/orders/**").authenticated();
```

## 5. Forbidden Ownership Test

```java
mockMvc.perform(get("/api/v1/orders/{id}", otherCustomerOrderId).with(user("customer-a")))
        .andExpect(status().isForbidden());
```
