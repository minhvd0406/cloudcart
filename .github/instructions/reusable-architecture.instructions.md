---
applyTo: "**/*.java"
description: "Reusable and extendable Spring design rules for interfaces, strategies, adapters, factories, registries, and utility classes."
---

# Purpose

Keep code reusable and extendable where it matters without adding abstraction for its own sake.

# Rules

- Prefer simple cohesive services for stable single-use behavior.
- Add interfaces when there are multiple implementations, external boundaries, test seams already used by the repo, or expected variability.
- Use strategies, factories, adapters, or handler registries for growing variants.
- Prefer configuration-driven strategy selection over long hardcoded if/else chains.
- Keep shared utilities stateless, generic, and free of business decisions.
- Keep business rules in domain/service code, not broad utility classes.
- Avoid inheritance for reuse; prefer composition.

# Good examples

```java
interface PaymentProvider {
    String name();

    PaymentResult charge(PaymentCommand command);
}

@Service
class PaymentRouter {
    private final Map<String, PaymentProvider> providers;

    PaymentRouter(List<PaymentProvider> providers) {
        this.providers = providers.stream()
                .collect(toUnmodifiableMap(PaymentProvider::name, Function.identity()));
    }
}
```

# Bad examples

```java
// Bad: every new provider expands this method and makes tests brittle.
PaymentResult charge(String provider, PaymentCommand command) {
    if ("primary".equals(provider)) {
        return primary.charge(command);
    }
    if ("backup".equals(provider)) {
        return backup.charge(command);
    }
    throw new IllegalArgumentException("Unknown provider");
}
```

# Good Copilot behavior

- Start simple, then introduce an extension point when variability is real.
- Inspect existing design patterns before adding interfaces or factories.
- Keep abstractions named around domain intent.

# Bad Copilot behavior

- Creating generic processors, managers, or helpers with unclear ownership.
- Moving order-specific rules into `CommonUtils`.
- Adding an interface for every service by default.

# Checklist

- Abstraction has a clear reason.
- Utility code is stateless and generic.
- Business rules stay in the domain/service layer.
- Variants are easy to add without changing unrelated code.

# Few-shot mini examples

1. Good: use `OrderProcessor` strategies when several order types vary.
2. Good: keep shared date formatting in a small stateless formatter when repeated.
3. Good: prefer composition over inheritance for provider-specific behavior.
4. Good: use a factory when construction varies by configuration.
5. Good: keep reusable code generic and free of one domain's assumptions.
6. Bad: create `CommonUtils` with order cancellation rules.
7. Bad: add a generic manager interface for one service.
8. Bad: move every private helper into a shared package.
9. Bad: couple a reusable component to a specific controller route.
10. Bad: add a plugin architecture for two simple branches.
