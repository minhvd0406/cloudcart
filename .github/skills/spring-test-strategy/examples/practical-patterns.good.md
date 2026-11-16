# Good Spring Test Examples

## 1. Pure Unit Test For Domain Rule

```java
@Test
void cancelOrder_shippedOrder_throwInvalidOrderStateException() {
    Order order = shippedOrder();

    assertThatThrownBy(() -> service.cancel(order.id()))
            .isInstanceOf(InvalidOrderStateException.class);
}
```

## 2. Boundary Test For Threshold

```java
@Test
void applyVipTag_totalEqualToThreshold_doNotAddVipOrderTag() {
    assertThat(service.applyVipTag(new BigDecimal("5000000")))
            .doesNotContain(OrderTag.VIP_ORDER);
}
```

## 3. MockMvc Validation Test

```java
mockMvc.perform(post("/api/v1/orders").contentType(APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.customerId").exists());
```

## 4. Repository Test Proves Database Query

```java
entityManager.persist(order("A", PAID));
entityManager.flush();
entityManager.clear();

assertThat(repository.findByCustomerIdAndStatus("A", PAID, PageRequest.of(0, 10)))
        .hasSize(1);
```

## 5. Reactive Test Uses StepVerifier

```java
StepVerifier.create(service.cancelOrder("missing"))
        .expectError(OrderNotFoundException.class)
        .verify();
```
