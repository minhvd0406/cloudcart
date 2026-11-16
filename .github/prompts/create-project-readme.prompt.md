# Purpose

Create or update a practical Spring Boot project README based on the target repository's actual files and conventions.

# User input template

```text
Project name:
Audience:
Known setup requirements:
Sections desired:
Commands already verified:
```

# Required context gathering

- Inspect existing README, build files, CI workflows, Docker files, scripts, configuration, migrations, and docs.
- Identify actual build tool, Java version, run command, test command, ports, and required environment variables.
- Do not infer unsupported technologies.

# Relevant instructions

- `readme-and-docs.instructions.md`
- `configuration.instructions.md`
- `testing.instructions.md`

# Relevant skills

- `readme-writer`
- `version-control-docs`

# Suggested agent

`readme-writer`

# Workflow

1. Build the README around what a new developer needs.
2. Use placeholders for values the repo does not reveal.
3. Include setup, configuration, run, test, database/migration, API/docs, troubleshooting, and contribution notes only when supported by repo files.
4. Keep commands copy-pasteable.
5. Avoid hardcoded organization URLs or secrets.

# Testing workflow

- Verify documented commands when practical.
- If not run, state that commands are based on repository files and remain unverified.

# Self-review checklist

- No invented dependencies, ports, or env vars.
- Secrets are placeholders.
- Commands match actual build files.
- README is concise and useful.

# Expected final response

- README sections created or updated.
- Commands verified or not run.
- Assumptions and placeholders.

# Few-shot examples

## Example request

Create README for a Spring Boot order service.

## Good Copilot behavior

- Inspect `pom.xml` or Gradle files before documenting commands.
- Use `{{SERVICE_NAME}}` if project name is unclear.
- Document required config without secret values.

## Good final response outline

- Updated README with setup, run, test, config, and troubleshooting.
- Did not verify commands because dependencies were unavailable.

## Bad Copilot behavior to avoid

- Assuming Gradle when only Maven exists.
- Adding PostgreSQL or OAuth2 sections without repo evidence.
- Pasting internal release links.
