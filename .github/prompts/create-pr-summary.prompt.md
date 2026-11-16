# Purpose

Create a concise pull request summary from actual changes, tests, risks, and review notes.

# User input template

```text
Diff or changed files:
Reason for change:
Tests run:
Risks or migrations:
Reviewer focus areas:
```

# Required context gathering

- Inspect the diff, changed tests, docs, migrations, and config.
- Use actual file changes rather than inferred scope.
- Check whether behavior, API, database, security, or operations changed.

# Relevant instructions

- `readme-and-docs.instructions.md`
- `testing.instructions.md`

# Relevant skills

- `version-control-docs`
- `readme-writer`

# Suggested agent

`readme-writer`

# Workflow

1. Summarize what changed and why.
2. List notable implementation details.
3. List tests run exactly.
4. Call out migrations, config, API, security, and compatibility impacts.
5. Keep language concise and reviewer-friendly.

# Testing workflow

- Include only commands actually run.
- If tests were not run, state that clearly.

# Self-review checklist

- No invented changes.
- No huge pasted diff.
- Risks are explicit.
- Reviewer focus is useful.

# Expected final response

- PR title suggestion.
- Summary.
- Tests.
- Risk/rollback notes.
- Reviewer checklist.

# Few-shot examples

## Example request

Create PR summary for order cancellation bug fix.

## Good Copilot behavior

- Mention shipped orders can no longer be cancelled.
- Mention regression test.
- Mention focused test command and result.

## Good final response outline

- Title: `fix(orders): prevent shipped order cancellation`
- Summary bullets.
- Tests run.
- Risks: state transition compatibility.

## Bad Copilot behavior to avoid

- Claiming full suite passed without evidence.
- Including unrelated implementation details.
- Hiding migration or API impacts.
