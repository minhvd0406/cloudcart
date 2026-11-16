# Purpose

Update a changelog with accurate, user-focused entries based on actual changes.

# User input template

```text
Version or Unreleased:
Change summary:
Diff or commits:
Breaking changes:
Migration notes:
Tests run:
```

# Required context gathering

- Inspect existing changelog format before editing.
- Inspect the diff or commits to avoid invented entries.
- Check whether changes are user-facing, internal, security, migration, or breaking.

# Relevant instructions

- `readme-and-docs.instructions.md`

# Relevant skills

- `version-control-docs`

# Suggested agent

`readme-writer`

# Workflow

1. Preserve existing changelog structure.
2. Add entries under the correct version and category.
3. Write from the user's perspective.
4. Mention breaking changes and migration steps clearly.
5. Keep entries concise.

# Testing workflow

- No code tests are expected for changelog-only edits.
- Run markdown checks if available.

# Self-review checklist

- Entries are based on actual changes.
- Categories match existing style.
- Dates and versions are correct when provided.
- No internal-only noise unless useful to users.

# Expected final response

- Changelog updated.
- Version/category changed.
- Markdown checks run or skipped.
- Assumptions.

# Few-shot examples

## Example request

Update changelog for fix preventing shipped orders from being cancelled.

## Good Copilot behavior

- Add a `Fixed` entry under `Unreleased` or the requested version.
- Mention the user-visible behavior.
- Note migration only if needed.

## Good final response outline

- Added changelog entry under `Fixed`.
- No tests run because docs-only change.

## Bad Copilot behavior to avoid

- Listing every internal file changed.
- Inventing a release date.
- Claiming tests passed.
