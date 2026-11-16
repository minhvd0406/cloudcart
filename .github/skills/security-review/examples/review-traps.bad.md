# Bad Security Review Examples

## 1. Trusts Client User

```java
boolean allowed = request.userId().equals(order.customerId());
```

## 2. Opens Protected Endpoint

```java
authorize.requestMatchers("/api/v1/orders/**").permitAll();
```

## 3. Logs Authorization Header

```java
log.info("authorization={}", request.getHeader("Authorization"));
```

## 4. Exposes Sensitive Response Field

```java
record UserResponse(Long id, String email, String passwordHash, String refreshToken) {
}
```

## 5. Unsafe CORS With Credentials

```java
configuration.setAllowedOrigins(List.of("*"));
configuration.setAllowCredentials(true);
```
