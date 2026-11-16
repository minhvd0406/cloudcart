---
applyTo: "**/{config,configuration}/**/*.java,**/*{Config,Configuration,Properties}.java,**/application*.{yml,yaml,properties}"
description: "Spring Boot configuration, profiles, typed properties, validation, and secrets handling rules."
---

# Purpose

Make configuration explicit, type-safe, validated, environment-aware, and safe for secrets.

# Rules

- Prefer grouped `@ConfigurationProperties` over scattered `@Value` fields.
- Group related properties by domain and capability.
- Use nested YAML objects for structured settings.
- Use placeholders for environment overrides.
- Put safe defaults in `application.yaml` where possible.
- Avoid default values in Java code unless required for backward compatibility or a safe fallback.
- Validate configuration properties with Bean Validation when invalid values should fail startup.
- Use safe defaults only for non-secret, low-risk settings.
- Do not default secrets, credentials, tokens, or private keys in code or committed config.
- Keep profile-specific config minimal and explicit.
- Use conditional beans, strategy maps, or handler registries when behavior is configuration-driven and expected to vary.
- Document required environment variables in README or operational docs when adding them.
- Keep test configuration local to tests and avoid changing production defaults to make tests pass.
- Do not assume a specific config format; follow the target repo's `yaml` or `properties` style.

# Good examples

```java
@ConfigurationProperties(prefix = "orders")
record OrderProperties(
        @Min(1) int pageSize,
        @NotBlank String defaultCurrency,
        Vip vip) {

    record Vip(@NotNull BigDecimal threshold, boolean enabled) {
    }
}
```

```yaml
orders:
  page-size: ${ORDERS_PAGE_SIZE:50}
  default-currency: ${ORDERS_DEFAULT_CURRENCY:VND}
  vip:
    enabled: ${ORDERS_VIP_ENABLED:true}
    threshold: ${ORDERS_VIP_THRESHOLD:5000000}
```

# Bad examples

```java
// Bad: scattered string keys are hard to validate and refactor together.
@Service
class OrderService {
    @Value("${orders.page-size:1000}")
    private int pageSize;

    @Value("${orders.api-token:dev-token}")
    private String apiToken;
}
```

```yaml
# Bad: flat, mixed concerns and unsafe secret-like default.
orders-page-size: 1000
orders-api-token: dev-token
vip-threshold: 5000000
```

# Good Copilot behavior

- Inspect existing config naming, profiles, and property binding style before adding keys.
- Add validation and docs for new required settings.
- Use placeholders for kit examples, not real secrets or internal hostnames.

# Bad Copilot behavior

- Adding secret defaults in `application.yml`.
- Inventing environment variable names without checking existing conventions.
- Spreading related config across unrelated services.
- Hardcoding thresholds, integration names, queue names, retries, or timeouts in Java code.

# Checklist

- Related settings are grouped.
- YAML is nested by domain/capability when YAML is used.
- Invalid config fails fast when appropriate.
- Secrets are externalized and not logged.
- Test config is isolated from production config.
- Important new properties are documented.

# Few-shot mini examples

1. Good: group client settings under `orders.client.*`.
2. Good: use `Duration` for timeouts and `DataSize` for payload limits.
3. Good: fail startup when required config is missing.
4. Good: use `<orders-api-token>` as a docs placeholder.
5. Good: override properties in tests with the project's existing test config style.
6. Bad: commit `api-token: dev-secret`.
7. Bad: hardcode a production queue name in a bean.
8. Bad: mutate a configuration bean with request-specific tenant data.
9. Bad: duplicate the same property with different names across profiles.
10. Bad: document a property that does not exist in code or config.
