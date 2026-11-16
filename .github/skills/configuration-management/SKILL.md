---
name: configuration-management
description: Use when reviewing or implementing Spring Boot configuration properties, profiles, secrets, feature flags, timeouts, retries, external resource names, validation, and documentation.
---

# Skill

Configuration management playbook for Spring Boot services.

# When to use

- Adding or reviewing `application*.yml`, `@ConfigurationProperties`, profiles, feature flags, thresholds, timeouts, retries, clients, or resource names.
- Replacing scattered `@Value` and literals with typed configuration.
- Checking secret handling and configuration documentation.

# When not to use

- Pure business logic with no configurable behavior.
- Replacing the target repository's config system without explicit approval.
- Cloud-console secret or infrastructure tasks outside repository evidence.

# Required context to inspect

- Existing application config, profiles, property classes, validation, config metadata, docs, deployment examples, tests, and secret patterns.
- Build and runtime conventions for property binding.
- Existing feature flags, toggles, timeout, retry, and client configuration.

# Step-by-step workflow

1. Identify which values truly vary by environment, tenant, deployment, or feature rollout.
2. Prefer grouped typed `@ConfigurationProperties`.
3. Add Bean Validation for required or bounded properties.
4. Keep secrets out of committed files and examples.
5. Preserve existing property naming and profile conventions.
6. Update docs with safe placeholders.
7. Add focused tests for binding or validation when behavior depends on config.

# Java best practices

- Use immutable records or classes for configuration properties when compatible.
- Use `Duration`, `DataSize`, enums, and typed collections instead of raw strings where appropriate.
- Keep config defaults explicit and safe.
- Avoid static access to Spring configuration.

# Spring Boot best practices

- Prefer `@ConfigurationProperties` over scattered `@Value`.
- Enable validation on properties according to project style.
- Keep environment-specific values in environment config or secret managers.
- Do not commit credentials, tokens, private keys, or connection strings.
- Use profiles sparingly and document their purpose.

# Multi-stack adaptation notes

- HTTP clients need configurable base URLs, timeouts, retry settings, and authentication references.
- Messaging clients need configurable queue/topic/binding names and retry/DLQ behavior.
- JPA/batch work may need configurable batch sizes and timeouts.
- Security needs configurable issuers, audiences, allowed origins, and public paths when conventions allow.

# Thread-safety rules

- Treat bound configuration objects as immutable values.
- Do not mutate configuration beans at request time.
- Avoid global mutable config holders.

# Configuration-driven design rules

- Use config for values that change by environment, deployment, feature rollout, integration, or operations.
- Keep true domain invariants as constants.
- Validate min/max bounds for page sizes, timeouts, retry counts, and thresholds.
- Keep property names generic and reusable across services.

# Testing requirements

- Add binding/validation tests for new property classes when config is non-trivial.
- Add behavior tests for feature flags, disabled states, thresholds, and invalid values.
- Verify docs mention required config without secret-shaped samples.

# Few-shot examples

1. Good: grouped properties record in `examples/practical-patterns.good.md`.
2. Good: validated timeout bounds in `examples/practical-patterns.good.md`.
3. Good: feature flag controls service branch in `examples/practical-patterns.good.md`.
4. Good: safe docs placeholder in `examples/practical-patterns.good.md`.
5. Good: binding validation test in `examples/practical-patterns.good.md`.
6. Bad: scattered `@Value` fields in `examples/review-traps.bad.md`.
7. Bad: secret committed to YAML in `examples/review-traps.bad.md`.
8. Bad: hardcoded resource name in service in `examples/review-traps.bad.md`.
9. Bad: mutable config bean changed per request in `examples/review-traps.bad.md`.
10. Bad: default disables validation silently in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- Configuration is typed, grouped, and validated.
- Secrets are not committed or documented as literal values.
- Defaults are safe.
- Varying values are config-driven.
- True invariants remain constants.
- Tests cover binding or config-driven behavior when relevant.

# Final response expectations

- Config files/classes changed or reviewed.
- Required properties and placeholders.
- Tests/commands run.
- Rollout or compatibility risks.
