---
name: readme-writer
description: Use when creating or updating README, setup, runbook, configuration, API, troubleshooting, contribution, and developer documentation for Spring Boot repositories.
---

# Skill

Spring Boot README and developer documentation playbook.

# When to use

- Creating or updating README files and developer guides.
- Documenting setup, run, test, configuration, database, API, troubleshooting, or contribution workflows.
- Updating docs after behavior, config, API, migration, or release process changes.
- Reviewing docs for copied assumptions or missing operational context.

# When not to use

- Code implementation without documentation changes.
- Changelog-only tasks better handled by `version-control-docs`.
- Marketing copy that does not need repository evidence.

# Required context to inspect

- Existing README, docs, build files, CI workflows, scripts, Docker files, compose files, application config, migrations, API docs, and test commands.
- Public API docs or generated OpenAPI files when present.
- Version files, Java toolchains, package manager metadata, and deployment docs when documenting setup.

# Step-by-step workflow

1. Inspect repository evidence before writing commands.
2. Preserve useful existing content and remove stale copied assumptions.
3. Organize docs around developer tasks: prerequisites, configuration, run, test, migrate, troubleshoot.
4. Use placeholders for values the repo does not reveal.
5. Document required configuration without exposing secrets.
6. Mention profiles, ports, database, cloud, and messaging only when files show them.
7. Keep examples generic and maintainable.
8. Verify commands when practical and state skipped checks clearly.

# Java best practices

- Document supported Java version only when repo files show it.
- Do not claim Java 21, Lombok, MapStruct, Testcontainers, Flyway, or Liquibase unless present.
- Keep class/package examples generic and reusable.

# Spring Boot best practices

- Document active profiles and required config safely.
- Document migration commands only when the repo provides them.
- Document actuator, metrics, or health endpoints only when configured.
- Document tests by actual build tool.
- Keep secrets in environment variables or secret managers, not docs.

# Multi-stack adaptation notes

- MVC/WebFlux: document actual base URLs and API docs only when present.
- JPA/Mongo/Cosmos: document local database setup, migrations, indexes, and seed data only from repo evidence.
- Azure/messaging/cache: document required resource names as placeholders and explain local-safe alternatives when present.
- Docker/Compose/Kubernetes: document commands only if files exist.

# Thread-safety rules

- For docs about services, call out stateless singleton expectations when relevant.
- Do not document patterns that encourage storing request/user state in beans.
- Document safe local test setup rather than shared mutable external resources.

# Configuration-driven design rules

- List required properties or environment variables without sample secret values.
- Prefer placeholder values such as `<orders-api-token>`.
- Document defaults only when present in config or code.
- Explain how to override profiles and property sources according to the repo.

# Testing requirements

- Verify documented commands when practical.
- Run markdown or link checks if available.
- State clearly when docs-only changes were not tested.
- Do not claim commands pass unless they were run.

# Few-shot examples

1. Good: setup section uses actual build tool in `examples/practical-patterns.good.md`.
2. Good: configuration table uses safe placeholders in `examples/practical-patterns.good.md`.
3. Good: test command is copied from CI/README evidence in `examples/practical-patterns.good.md`.
4. Good: troubleshooting entry names symptom and fix in `examples/practical-patterns.good.md`.
5. Good: API docs link is conditional on existing OpenAPI setup in `examples/practical-patterns.good.md`.
6. Bad: Gradle command invented for Maven repo in `examples/review-traps.bad.md`.
7. Bad: secret-shaped sample value in docs in `examples/review-traps.bad.md`.
8. Bad: internal organization URL copied into reusable kit in `examples/review-traps.bad.md`.
9. Bad: database setup invented without compose/config evidence in `examples/review-traps.bad.md`.
10. Bad: test pass claimed without command result in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- Commands come from repo files or are placeholders.
- No secrets, internal links, or copied identifiers.
- Setup path is clear.
- Configuration is documented safely.
- Docs match current behavior.
- Verification is honest.

# Final response expectations

- Documentation files changed and sections updated.
- Commands checked or skipped.
- Assumptions and placeholders.
- Remaining documentation gaps.
