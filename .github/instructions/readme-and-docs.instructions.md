---
applyTo: "**/*{README,CHANGELOG,CONTRIBUTING,docs,guide,manual}*.md,docs/**/*.md"
description: "Documentation rules for Spring Boot README, changelog, setup, API, configuration, and release notes."
---

# Purpose

Keep documentation accurate, practical, and aligned with the actual repository.

# Rules

- Inspect existing README, build files, CI, configuration, Docker files, and scripts before documenting commands.
- Do not invent setup steps, ports, environment variables, dependencies, or release processes.
- Document both Maven and Gradle only if the target repository supports both.
- Prefer copy-pasteable commands with placeholders for project-specific values.
- Document required configuration without exposing secrets.
- Keep changelog entries user-focused and grouped by added, changed, fixed, removed, security, or migration impact when the repo follows that style.
- Link to local files when useful instead of hardcoded organization URLs.
- Keep docs concise and maintainable.

# Good examples

````markdown
## Run Tests

Use the command configured by this repository:

```bash
{{TEST_COMMAND}}
```
````

# Bad examples

```markdown
<!-- Bad: assumes Gradle and a version file that may not exist. -->
Run `./gradlew test` and update `build.gradle` before every release.
```

# Good Copilot behavior

- Verify commands against repository files before writing them.
- Use placeholders when the kit cannot know project values.
- Update docs when public API, config, setup, or release workflow changes.

# Bad Copilot behavior

- Copying release URLs or company-specific process from another project.
- Claiming a command works without repository evidence.
- Adding long generic tutorials that do not help the target repo.

# Checklist

- Commands exist or are marked as placeholders.
- No secrets or internal URLs are documented.
- Docs match current files.
- Changed behavior has user-facing documentation when needed.

# Few-shot mini examples

1. Good: copy the test command from CI or build docs.
2. Good: document `ORDERS_API_TOKEN=<orders-api-token>` without a real-looking value.
3. Good: mention a new config key when code adds it.
4. Good: document migration order when schema rollout requires it.
5. Good: keep API examples aligned with current DTO fields.
6. Bad: invent `./gradlew bootRun` for a Maven-only repo.
7. Bad: include internal release URLs in reusable docs.
8. Bad: document PostgreSQL when the repo has no database evidence.
9. Bad: claim tests pass without command output.
10. Bad: paste a generic Spring tutorial that ignores repository setup.
