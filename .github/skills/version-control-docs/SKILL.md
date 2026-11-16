---
name: version-control-docs
description: Use when documenting branch, commit, pull request, changelog, tagging, release, versioning, migration, and reviewer workflows for reusable Spring Boot repositories.
---

# Skill

Version-control, changelog, and release documentation playbook.

# When to use

- Creating or updating branch, commit, PR, changelog, versioning, tag, release, or reviewer docs.
- Creating PR summaries, release notes, or changelog entries from actual changes.
- Reviewing release documentation for copied project assumptions.
- Documenting API, migration, security, or configuration impact in version-control workflows.

# When not to use

- Runtime application documentation without version-control content.
- Implementing code changes.
- Inventing release policy where the repo provides no evidence.

# Required context to inspect

- Existing README, CONTRIBUTING, CHANGELOG, release docs, CI workflows, version files, package metadata, tags guidance, and PR templates.
- Repository conventions around conventional commits, SemVer, branch names, issue links, migrations, and release approvals when documented.
- Actual diff when writing PR summaries or changelog entries.

# Step-by-step workflow

1. Preserve existing workflow where it exists.
2. Read the actual diff before summarizing changes.
3. Use generic examples and placeholders.
4. Avoid hardcoded organization URLs and copied repository names.
5. Keep branch, commit, PR, changelog, and release sections concise.
6. Call out behavior, API, migration, config, security, and compatibility impact.
7. State unknowns instead of inventing policy.
8. Report tests exactly as provided or observed.

# Java best practices

- Mention Java or Spring Boot version changes only when actual files changed.
- Keep version notes user-facing rather than implementation trivia.
- Call out migration or rollback notes for persistence and configuration changes.

# Spring Boot best practices

- Mention migrations, API contracts, actuator/ops changes, and configuration impact in PR/release docs when relevant.
- Do not assume Maven or Gradle release steps without evidence.
- Include test commands and results only when known.

# Multi-stack adaptation notes

- JPA/Mongo/Cosmos changes may require migration/backfill/index notes.
- Messaging changes may need replay, idempotency, DLQ, or compatibility notes.
- Security changes should call out auth behavior and negative tests.
- Observability changes should note dashboard/alert or log format impact when relevant.

# Thread-safety rules

- Call out concurrency, locking, idempotency, or mutable-state risks in PR/release notes when changed.
- Do not hide thread-safety risk behind generic "refactor" wording.

# Configuration-driven design rules

- PR summaries and release notes should call out new, renamed, removed, or default-changed configuration keys.
- Use placeholders for environment-specific values.
- Include rollback notes when config rollout order matters.

# Testing requirements

- No code tests expected for docs-only changes.
- Run markdown checks if available.
- Verify links when practical.
- For PR summaries, include only commands actually run.

# Few-shot examples

1. Good: PR checklist mentions tests/config/API in `examples/practical-patterns.good.md`.
2. Good: changelog entry is user-facing in `examples/practical-patterns.good.md`.
3. Good: release note calls out migration order in `examples/practical-patterns.good.md`.
4. Good: conventional commit example is generic in `examples/practical-patterns.good.md`.
5. Good: PR summary includes exact test command in `examples/practical-patterns.good.md`.
6. Bad: release URL hardcodes organization in `examples/review-traps.bad.md`.
7. Bad: "all tests pass" without evidence in `examples/review-traps.bad.md`.
8. Bad: changelog repeats low-level file names only in `examples/review-traps.bad.md`.
9. Bad: migration risk hidden in generic summary in `examples/review-traps.bad.md`.
10. Bad: breaking API change omitted in `examples/review-traps.bad.md`.

# Checklist

Use `checklist.md` for the full review list. Minimum checks:

- Workflow matches repository evidence.
- Examples are generic.
- Changelog entries are user-facing.
- Tags/releases are placeholders unless known.
- Tests and risks are reported honestly.
- Migration, API, config, and security impacts are visible.

# Final response expectations

- Docs updated or summary generated.
- Assumptions/placeholders listed.
- Markdown/link checks or skipped checks reported.
- Review or release risks called out.
