# Good README Examples

## 1. Build Tool From Evidence

````markdown
## Run Tests

Use the repository wrapper:

```bash
./mvnw test
```
````

## 2. Safe Configuration Table

```markdown
| Variable | Required | Description |
|---|---:|---|
| `ORDERS_API_BASE_URL` | Yes | Base URL for the orders dependency. |
| `ORDERS_API_TOKEN` | Yes | Token provided by the deployment secret manager. |
```

## 3. Placeholder Instead Of Secret

```bash
export ORDERS_API_TOKEN="<orders-api-token>"
```

## 4. Practical Troubleshooting

```markdown
### Database connection refused

Start the local database service defined in `compose.yaml`, then rerun the app.
```

## 5. Conditional API Docs

```markdown
OpenAPI UI is available at `/swagger-ui/index.html` when the `springdoc-openapi` dependency is present.
```
