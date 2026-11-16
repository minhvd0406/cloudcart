# Bad Spring Test Examples

## 1. Full Context For Pure Logic

```java
@SpringBootTest
class OrderTagServiceTest {
    @Test
    void testTags() {
        assertThat(service.applyVipTag(new BigDecimal("5000001"))).isNotEmpty();
    }
}
```

## 2. Mock Interaction Without Behavior

```java
service.cancel(orderId);
verify(orderRepository).save(any(Order.class));
```

## 3. Flaky Current Time

```java
assertThat(service.isExpired(Instant.now().minusSeconds(1))).isTrue();
```

## 4. External Resource In Ordinary Test

```java
BlobClient client = new BlobClientBuilder()
        .connectionString(System.getenv("PROD_STORAGE_CONNECTION"))
        .buildClient();
```

## 5. Coverage Padding

```java
@Test
void getStatus_returnsStatus() {
    assertThat(order.getStatus()).isEqualTo(order.getStatus());
}
```
