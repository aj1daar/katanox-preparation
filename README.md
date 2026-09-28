# Katanox — Pairing Practice

Five tasks, increasing difficulty, all Java 25. Run any time with Maven.

## IntelliJ setup (required)

Records and enhanced switch will show red errors unless both of these match:

- **Project SDK**: `temurin-25` (or whichever JDK 25 you have installed)
  → File → Project Structure → Project → SDK
- **Language level**: `25`
  → File → Project Structure → Project → Language level

## Running tests

```bash
# all tasks
mvn test

# one task at a time
mvn test -Dtest="Task01*"
mvn test -Dtest="Task02*"
mvn test -Dtest="Task03*"
mvn test -Dtest="Task04*"
mvn test -Dtest="Task05*"
```

## Tasks

| # | Theme | Target time |
|---|-------|-------------|
| 01 | Rate update deduplication | 25 min |
| 02 | Availability range merging | 30 min |
| 03 | Booking normalisation (read existing code first) | 30 min |
| 04 | Time-windowed reservation query | 20 min |
| 05 | Cross-system reconciliation at scale | 35 min |

Each task has its own `README.md` with the full brief. Starter code and
failing tests are already in place.

`SOLUTION-DO-NOT-OPEN.md` files exist in each task package. Do not open them
until you have finished the task.
