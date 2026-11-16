---
applyTo: "src/test/**/*.{java,kt}"
description: "Spring Boot unit, MockMvc, DataJpaTest, integration, regression, boundary, and negative test rules."
---

# Purpose

Guide Copilot toward tests that prove behavior at the right layer without over-mocking or overloading the Spring context.

# Rules

- Add or update tests for every behavior change.
- Aim for meaningful coverage above 90% for new or changed code.
- Do not chase coverage with meaningless tests.
- Use JUnit 5 style and follow existing Mockito, AssertJ, Hamcrest, or project-specific conventions.
- Name unit tests with `methodName_input_expectation`.
- Use `@ExtendWith(MockitoExtension.class)` for pure Mockito unit tests when appropriate.
- Use unit tests for business rules and pure Java logic.
- Use MockMvc/WebMvc tests for controller request, validation, status, security, and response behavior.
- Use WebTestClient and StepVerifier for reactive controller/service behavior.
- Use `@DataJpaTest` for repository queries and mappings.
- Use Mongo-focused tests for Mongo repositories or MongoTemplate when appropriate.
- Use integration tests when Spring wiring, transactions, serialization, messaging, or infrastructure matters.
- Use Testcontainers only when the project already uses it or realistic external infrastructure is explicitly required.
- Add regression tests for bug fixes.
- Cover boundary values, negative paths, validation failures, authorization failures, and conflict cases.
- Avoid tests that only verify mocks and no observable behavior.
- Do not mock the system under test.
- Run the narrowest relevant test command and report what actually ran.

# Good examples

```java
@Test
void applyVipTag_totalGreaterThanThreshold_addVipOrderTag() {
    Order order = new Order(new BigDecimal("5000001"));

    order.applyVipTag(new BigDecimal("5000000"));

    assertThat(order.tags()).contains(OrderTag.VIP_ORDER);
}
```

```java
@Test
void createOrder_invalidRequest_returnBadRequest() throws Exception {
    mockMvc.perform(post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{}"))
            .andExpect(status().isBadRequest());
}
```

```java
@Test
void findByCustomerIdAndStatus_validFilter_returnPagedOrders() {
    Page<Order> result = repository.findByCustomerIdAndStatus(1L, OrderStatus.PAID, PageRequest.of(0, 10));

    assertThat(result.getContent()).allMatch(order -> order.customerId().equals(1L));
}
```

```java
@Test
void findOrder_reactiveRepositoryError_propagateError() {
    when(repository.findById("order-1")).thenReturn(Mono.error(new TimeoutException("timeout")));

    StepVerifier.create(service.findOrder("order-1"))
            .expectError(TimeoutException.class)
            .verify();
}
```

# Bad examples

```java
// Bad: verifies an implementation detail without proving the business outcome.
verify(orderRepository).save(any(Order.class));
```

```java
// Bad: only happy-path status check; no response shape, validation, or security coverage.
mockMvc.perform(get("/api/v1/orders/1"))
        .andExpect(status().isOk());
```

# Good Copilot behavior

- Inspect existing test libraries, fixtures, naming, and build commands first.
- Prefer small focused tests before full-context tests.
- Add one regression test that fails before the bug fix when possible.

# Bad Copilot behavior

- Recommending Testcontainers in a repo that has no infrastructure for it.
- Writing tests that pass even if the behavior is removed.
- Claiming tests passed without running them.
- Starting `@SpringBootTest` for pure service logic.
- Naming tests vaguely, such as `testCancelOrder`.

# Checklist

- Behavior change has a test.
- Test layer matches the risk.
- Happy path and important negative path are covered.
- Tests are deterministic and isolated.
- Commands run are reported accurately.
- Unit test names follow `methodName_input_expectation`.
- Coverage for new/changed code is meaningful, not padded.

# Few-shot mini examples

1. Good: unit test `cancelOrder_shippedOrder_throwInvalidOrderStateException`.
2. Good: MockMvc test asserts `400` and validation error body.
3. Good: repository test covers filter, sort, pagination, and empty result.
4. Good: use fixed `Clock` for time-sensitive rules.
5. Good: run the focused test class before broader suites.
6. Bad: use `@SpringBootTest` for a pure calculation method.
7. Bad: verify only `repository.save()` and call it behavior coverage.
8. Bad: use sleeps to wait for async behavior.
9. Bad: depend on test execution order or shared static state.
10. Bad: claim tests passed without running the command.
