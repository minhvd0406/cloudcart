# Purpose

Create or update version-control documentation for branches, commits, pull requests, changelogs, tags, and releases using repository evidence.

# User input template

```text
Repository workflow:
Release style:
Branch naming preferences:
Commit convention:
Existing docs to preserve:
```

# Required context gathering

- Inspect existing README, CONTRIBUTING, CHANGELOG, release docs, CI workflows, branch protections if documented, and package version files.
- Identify whether the repo uses SemVer, conventional commits, tags, release branches, or trunk-based development.
- Use placeholders when the repository does not define a rule.

# Relevant instructions

- `readme-and-docs.instructions.md`

# Relevant skills

- `version-control-docs`
- `readme-writer`

# Suggested agent

`readme-writer`

# Workflow

1. Preserve documented workflow that already exists.
2. Add missing sections only when useful.
3. Keep branch, commit, PR, changelog, and release guidance concise.
4. Use generic examples such as `feature/order-summary`.
5. Avoid hardcoded repository URLs.

# Testing workflow

- No code tests are expected.
- Check markdown formatting and links when tools are available.

# Self-review checklist

- Docs do not conflict with existing workflow.
- Examples are generic.
- Release steps do not assume build tool or hosting details.
- No internal URLs.

# Expected final response

- Version-control docs updated.
- Assumptions and placeholders.
- Markdown checks run or skipped.

# Few-shot examples

## Example request

Create version-control README for a Spring Boot repo using conventional commits.

## Good Copilot behavior

- Document branch naming, commit format, PR checklist, changelog, and tag rules with placeholders.
- Avoid assuming release automation.

## Good final response outline

- Added version-control guide.
- Used generic branch and commit examples.
- Markdown linter unavailable.

## Bad Copilot behavior to avoid

- Linking to a specific organization's release page.
- Assuming `build.gradle` version updates.
- Requiring squash merge if repo does not say so.
