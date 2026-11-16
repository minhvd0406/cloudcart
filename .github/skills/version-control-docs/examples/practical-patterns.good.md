# Good Version-Control Documentation Examples

## 1. PR Checklist

```markdown
- Tests added or updated for behavior changes.
- Configuration, migration, and API impacts documented.
- Commands run are listed with results.
```

## 2. User-Facing Changelog

```markdown
### Fixed

- Prevent shipped orders from being cancelled and return a conflict response.
```

## 3. Migration-Aware Release Note

```markdown
Deploy the additive migration before enabling `orders.cancellation-reason.required`.
```

## 4. Generic Conventional Commit

```text
fix(orders): reject cancellation for shipped orders
```

## 5. Exact Test Command

```markdown
Tests: `./mvnw -Dtest=OrderServiceTest test` passed.
```
