---
applyTo: "src/test/**/*.{java,kt}"
description: "Unit test coverage rules for meaningful >90% coverage, JUnit 5 naming, Mockito, boundary tests, and anti-patterns."
---

# Purpose

Increase confidence in new and changed code with meaningful tests rather than hollow coverage.

# Rules

- Aim for meaningful coverage above 90% for new or changed code.
- Do not write tests that only execute lines without asserting behavior.
- Use JUnit 5 style.
- Follow existing Mockito, AssertJ, Hamcrest, and fixture conventions.
- Name unit tests with `methodName_input_expectation`.
- Use `@ExtendWith(MockitoExtension.class)` for pure Mockito unit tests when appropriate.
- Prefer pure unit tests for business logic.
- Avoid `@SpringBootTest` unless wiring or full integration is necessary.
- Add regression tests for bugs and boundary tests for thresholds.
- Use test data builders/factories when setup repetition starts hiding intent.

# Good examples

```java
@ExtendWith(MockitoExtension.class)
class OrderTagServiceTest {
    @Test
    void applyVipTag_totalEqualToThreshold_doNotAddVipOrderTag() {
        OrderTagService service = new OrderTagService(vipThreshold("5000000"));

        Set<OrderTag> tags = service.applyVipTag(new BigDecimal("5000000"));

        assertThat(tags).doesNotContain(OrderTag.VIP_ORDER);
    }
}
```

# Bad examples

```java
// Bad: vague name and no meaningful behavior assertion.
@Test
void testApplyVipTag() {
    service.applyVipTag(new BigDecimal("5000001"));
    verify(repository).save(any());
}
```

# Good Copilot behavior

- Inspect existing test names before adding tests, then use this naming rule for new unit tests when compatible.
- Prefer behavior assertions over interaction assertions.
- Add focused tests for disabled toggles, equal thresholds, and invalid inputs.

# Bad Copilot behavior

- Starting Spring for pure logic.
- Padding coverage with getter/setter tests.
- Ignoring boundary and negative cases.

# Checklist

- New/changed code has meaningful coverage.
- Unit test names follow `methodName_input_expectation`.
- Tests assert behavior.
- Boundary and negative cases are covered.
- Spring context is avoided unless needed.

# Few-shot mini examples

1. Good: test below, equal, and above threshold values.
2. Good: assert thrown domain exception type and message when message is part of behavior.
3. Good: use Mockito only for true collaborators.
4. Good: use builders when fixture setup hides test intent.
5. Good: add a regression test before or with a bug fix.
6. Bad: write tests only for getters and setters to raise coverage.
7. Bad: use vague names like `testService`.
8. Bad: mock the class under test.
9. Bad: assert only that a mock method was called when state/output is observable.
10. Bad: start Spring for a branch with no Spring behavior.
