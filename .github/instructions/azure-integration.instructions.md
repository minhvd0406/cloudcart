---
applyTo: "**/*{Azure,ServiceBus,EventHub,Blob,Queue,Topic,Stream,Consumer,Producer,Client,Configuration,Properties}.java,**/application*.{yml,yaml,properties}"
description: "Azure SDK, Service Bus, Stream Binder, and cloud integration rules for configuration, idempotency, retries, DLQ, and safe logging."
---

# Purpose

Keep Azure and message-driven integrations configuration-driven, testable, idempotent, and safe for production operations.

# Rules

- Azure SDK clients should be Spring beans configured from typed properties.
- Endpoints, namespaces, queue/topic names, binding names, retry settings, and timeouts must be configuration-driven.
- Do not hardcode secrets, connection strings, resource names, tenant IDs, or subscription IDs.
- Message handlers must be idempotent.
- Validate message payloads before applying side effects.
- Understand retry and dead-letter behavior before swallowing exceptions.
- Do not catch and ignore failures that should trigger retry or DLQ.
- Do not log secrets, connection strings, tokens, or sensitive payloads.
- Add metrics/logs with safe message IDs, operation names, and outcomes.

# Good examples

```java
@ConfigurationProperties(prefix = "integrations.orders.service-bus")
record OrderBusProperties(
        @NotBlank String queueName,
        @NotNull Duration processingTimeout) {
}
```

```java
void handle(OrderEvent event) {
    if (processedMessageRepository.existsByMessageId(event.messageId())) {
        return;
    }
    validator.validate(event);
    orderService.apply(event);
    processedMessageRepository.markProcessed(event.messageId());
}
```

# Bad examples

```java
// Bad: hardcoded cloud resource and swallowed exception prevents retry/DLQ.
try {
    serviceBusClient.send("orders-prod", payload);
} catch (Exception ignored) {
}
```

# Good Copilot behavior

- Inspect existing Azure SDK, Spring Cloud Stream, Service Bus, retry, and DLQ patterns.
- Keep resource names and timeouts in configuration.
- Add tests for idempotency and invalid payloads.

# Bad Copilot behavior

- Creating SDK clients manually inside each service method.
- Logging full message payloads.
- Hiding handler exceptions that infrastructure should retry.

# Checklist

- Clients/resources are configuration-driven.
- Handler is idempotent.
- Payload is validated.
- Retry/DLQ behavior is preserved.
- Sensitive data is not logged.

# Few-shot mini examples

1. Good: bind `integrations.orders.service-bus.queue-name` into typed properties.
2. Good: create Azure SDK clients as Spring beans instead of inside each handler method.
3. Good: persist processed `messageId` before acknowledging a command that must be idempotent.
4. Good: log `messageId`, event type, and outcome without payload secrets.
5. Good: allow infrastructure retry/DLQ by rethrowing transient handler failures.
6. Bad: send to literal queue name `orders-prod` from service code.
7. Bad: catch `Exception` and ignore it inside a Service Bus listener.
8. Bad: log connection strings, SAS tokens, or full message bodies.
9. Bad: mutate database state before validating event schema and required fields.
10. Bad: create a new retry policy that duplicates broker retries without limits.
