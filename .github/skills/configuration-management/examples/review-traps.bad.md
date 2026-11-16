# Bad Configuration Examples

## 1. Scattered Value Fields

```java
@Value("${orders.client.base-url}")
private String baseUrl;
```

## 2. Secret In YAML

```yaml
orders:
  client:
    token: "dev-secret-123"
```

## 3. Hardcoded Resource Name

```java
serviceBusClient.send("orders-prod", event);
```

## 4. Mutable Runtime Config

```java
properties.setCurrentTenant(request.tenantId());
```

## 5. Unsafe Silent Default

```java
boolean validationEnabled = false;
```
