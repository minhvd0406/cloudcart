---
applyTo: "**/*.{java,yml,yaml,properties}"
description: "Configuration-driven behavior rules for thresholds, toggles, strategy selection, retries, timeouts, and variants."
---

# Purpose

Keep behavior adaptable across services and environments without scattering magic values or growing hardcoded condition chains.

# Rules

- Use typed `@ConfigurationProperties` for thresholds, toggles, variant behavior, retries, timeouts, integration names, binding names, cache names, and feature flags.
- Prefer config-driven strategy selection when business variants are expected to grow.
- Use constants for repeated literals that are not runtime configuration.
- Avoid hardcoded if/else chains for expanding product, region, channel, tenant, or integration variants.
- Use conditional beans, `Map<String, Strategy>`, ordered lists, or handler registries only when variability is real.
- Validate required config at startup.
- Document important new config properties.
- Keep defaults safe and do not default secrets.

# Good examples

```java
@ConfigurationProperties(prefix = "orders.tags")
record OrderTagProperties(
        boolean vipEnabled,
        @NotNull BigDecimal vipThreshold) {
}
```

```java
@Service
class OrderTagService {
    private final OrderTagProperties properties;

    OrderTagService(OrderTagProperties properties) {
        this.properties = properties;
    }

    Set<OrderTag> tagsFor(BigDecimal total) {
        if (properties.vipEnabled()
                && total.compareTo(properties.vipThreshold()) > 0) {
            return Set.of(OrderTag.VIP_ORDER);
        }
        return Set.of();
    }
}
```

# Bad examples

```java
// Bad: threshold and feature behavior are hardcoded in business logic.
if (total.compareTo(new BigDecimal("5000000")) > 0) {
    tags.add(OrderTag.VIP_ORDER);
}
```

```java
// Bad: this chain will grow every time a new provider is added.
if ("azure".equals(provider)) {
    return azureClient;
} else if ("internal".equals(provider)) {
    return internalClient;
}
```

# Good Copilot behavior

- Inspect existing property naming and strategy patterns before adding config.
- Use simple typed properties for one variant and a strategy map only when variability is real.
- Add tests for enabled/disabled toggles and threshold boundaries.

# Bad Copilot behavior

- Creating a factory framework for a single stable behavior.
- Adding default secrets or resource names in Java code.
- Inventing environment variable names without checking repository conventions.

# Checklist

- Magic values became constants or typed configuration.
- Growing variants have a clear extension point.
- Config is validated and documented.
- Tests cover toggles, thresholds, and selected variants.

# Few-shot mini examples

1. Good: use `orders.vip.threshold` for a tunable VIP cutoff.
2. Good: validate retry count with `@Min(0)` and `@Max(10)`.
3. Good: use a strategy list when payment methods have separate behavior.
4. Good: keep a legal domain invariant as a named constant, not environment config.
5. Good: test feature flag on and off paths.
6. Bad: hardcode `5000000` in three services.
7. Bad: scatter `@Value("${timeout}")` across unrelated beans.
8. Bad: add an interface for one implementation with no variability.
9. Bad: invent environment variable names without checking existing config.
10. Bad: let invalid property values silently fall back to unsafe defaults.
