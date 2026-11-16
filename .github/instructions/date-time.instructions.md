---
applyTo: "**/*.java"
description: "Java date/time rules for Instant, LocalDate, time zones, clocks, persistence, and deterministic tests."
---

# Purpose

Make time handling explicit, testable, and safe across APIs, persistence, and business rules.

# Rules

- Use `java.time` types, not legacy `Date` or `Calendar`, in new code.
- Use `Instant` for machine timestamps and audit fields.
- Use `LocalDate` for date-only business concepts.
- Use `ZonedDateTime` or `OffsetDateTime` only when the offset or zone is part of the contract.
- Inject `Clock` where current time affects business behavior.
- Keep API time formats consistent with the target repository.
- Avoid hidden system-default time zone behavior.
- Write deterministic tests for time-sensitive logic.

# Good examples

```java
@Service
class OrderExpirationService {
    private final Clock clock;

    OrderExpirationService(Clock clock) {
        this.clock = clock;
    }

    boolean isExpired(Instant expiresAt) {
        return expiresAt.isBefore(Instant.now(clock));
    }
}
```

# Bad examples

```java
// Bad: hard to test and depends on machine clock directly.
boolean isExpired(Instant expiresAt) {
    return expiresAt.isBefore(Instant.now());
}
```

# Good Copilot behavior

- Inspect existing serialization, database mapping, and clock patterns first.
- Use fixed clocks in tests.
- Preserve API compatibility for existing timestamp fields.

# Bad Copilot behavior

- Mixing `LocalDateTime` and system default zones for audit timestamps.
- Adding current-time calls deep inside domain logic without a way to test them.
- Changing time formats in public APIs without request.

# Checklist

- Time type matches business meaning.
- Current time is testable.
- Time zone assumptions are explicit.
- Tests use deterministic clocks.

# Few-shot mini examples

1. Good: use `Instant` for audit timestamps.
2. Good: use `LocalDate` for a delivery date with no time of day.
3. Good: inject `Clock` into services that compare against current time.
4. Good: use a fixed clock in expiration tests.
5. Good: preserve existing API timestamp format during refactors.
6. Bad: use `LocalDateTime.now()` for persisted audit fields.
7. Bad: rely on the server default time zone for business cutoffs.
8. Bad: use `Date` or `Calendar` in new code.
9. Bad: compare date strings lexically instead of using `java.time`.
10. Bad: change public time format without a compatibility request.
