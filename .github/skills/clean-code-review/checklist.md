# Clean Code Review Checklist

- Inspect surrounding code before recommending style changes.
- Prioritize correctness, user impact, data safety, and missing tests before readability nits.
- Check class and method cohesion.
- Check names reveal domain intent.
- Check controllers, services, repositories, DTOs, and mappers stay in their lanes.
- Check abstractions have real variability or testability value.
- Check duplication before introducing helpers.
- Check mutable singleton/static state.
- Check literals that should be constants or typed configuration.
- Check stream/lambda usage remains readable and side-effect free.
- Check tests assert behavior and protect refactors.
