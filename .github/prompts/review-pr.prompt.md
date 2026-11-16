# Purpose

Review a Spring Boot pull request for correctness, regressions, missing tests, maintainability, and compatibility with repository conventions.

# User input template

```text
PR/diff to review:
Areas of concern:
Files changed:
Tests run by author:
```

# Required context gathering

- Inspect the diff and nearby unchanged code.
- Inspect tests and public contracts affected by the diff.
- Check build/config/migration changes when present.
- Identify stack-specific risks: MVC/WebFlux, virtual threads, JPA, MongoDB, Cosmos DB, Azure, messaging, cache, or scheduled jobs.

# Relevant instructions

- `spring-layering.instructions.md`
- `java-code-style.instructions.md`
- `testing.instructions.md`
- `security.instructions.md`
- `data-access.instructions.md`
- `config-driven-design.instructions.md`
- `thread-safety.instructions.md`
- `multi-stack-spring.instructions.md`

# Relevant skills

- `clean-code-review`
- `spring-test-strategy`
- `api-contract-review`
- `security-review`
- `jpa-persistence-review`

# Suggested agent

`clean-code-reviewer`

# Workflow

1. Prioritize bugs, regressions, security, data loss, and missing tests.
2. Rank findings by severity.
3. Reference file and line when possible.
4. Check hardcoded thresholds/toggles/variants, thread-safety, stack boundaries, and test quality.
5. Avoid style-only comments unless they affect maintainability.
6. Include open questions separately.

# Testing workflow

- Verify whether tests were added or updated.
- Check whether new/changed code has meaningful coverage and unit test names follow `methodName_input_expectation` where applicable.
- Do not claim to have run tests unless you did.
- Suggest focused test gaps.

# Stop and ask when

- The diff is incomplete or stack context is unavailable.
- Security/authorization expectations are required but missing.
- The requested review scope conflicts with repository conventions.

# Self-review checklist

- Findings are actionable and grounded.
- Severity is clear.
- No speculative nitpicks dominate the review.
- Summary is secondary to findings.

# Expected final response

- Findings first, ordered by severity.
- Open questions or assumptions.
- Brief change summary.
- Test gaps and residual risk.

# Few-shot examples

## Example request

Review PR that adds order cancellation endpoint.

## Good Copilot behavior

- Check authorization, state transitions, transaction boundary, DTO exposure, error mapping, and tests.
- Flag missing `SHIPPED` negative test if absent.

## Good final response outline

- High: cancellation allows shipped orders.
- Medium: controller returns entity.
- Test gap: no forbidden-access test.

## Bad Copilot behavior to avoid

- Starting with a broad compliment.
- Listing style preferences before correctness issues.
- Approving without checking tests.
