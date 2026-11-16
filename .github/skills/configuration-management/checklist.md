# Configuration Management Checklist

- Inspect existing config files, profiles, properties classes, docs, and tests.
- Prefer grouped `@ConfigurationProperties`.
- Use typed values such as `Duration`, `DataSize`, enums, and collections.
- Validate required and bounded values.
- Keep secrets out of committed files and docs.
- Use safe placeholders in examples.
- Keep property names aligned with existing conventions.
- Avoid scattered `@Value`.
- Avoid mutable config changes at request time.
- Add tests for property binding, invalid config, feature flags, thresholds, and disabled behavior when relevant.
