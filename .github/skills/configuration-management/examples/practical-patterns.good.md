# Good Configuration Examples

## 1. Grouped Properties

```java
@ConfigurationProperties(prefix = "orders.client")
record OrderClientProperties(
        @NotBlank String baseUrl,
        @NotNull Duration connectTimeout,
        @NotNull Duration readTimeout) {
}
```

## 2. Validated Bounds

```java
record SearchProperties(@Min(1) @Max(200) int maxPageSize) {
}
```

## 3. Feature Flag

```java
if (!properties.vipTaggingEnabled()) {
    return tags;
}
```

## 4. Safe Docs Placeholder

```bash
export ORDERS_CLIENT_TOKEN="<orders-client-token>"
```

## 5. Binding Validation Test

```java
contextRunner.withPropertyValues("orders.search.max-page-size=0")
        .run(context -> assertThat(context).hasFailed());
```
