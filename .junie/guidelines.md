# Project Development Guidelines

## Build & Configuration

### Prerequisites
- Java SDK 21
- MongoDB (for Spring Data MongoDB)
- npm package manager (for Vaadin frontend)

### Development Environment Setup
```bash
# Clone the repository
git clone <repository-url>
cd utility

# Install frontend dependencies
npm install

# Configure MongoDB connection in application.properties
# Default configuration:
spring.data.mongodb.database=utility
```

### Build Commands
```bash
# Build the project
./mvnw clean package

# Run the application
./mvnw spring-boot:run

# Build for production
./mvnw clean package -Pproduction
```

## Testing

### Unit Testing
Here's an example of a simple test for the CronUtils class:

```java
@SpringBootTest
class CronUtilsTest {
    
    @Test
    void testDescriviCron() throws CronValidationException {
        // Test for daily execution at midnight
        String cron = "0 0 0 * * *";
        String expected = "Esegue tutti i giorni a mezzanotte";
        assertEquals(expected, CronUtils.descriviCron(cron));
        
        // Test for execution every minute
        cron = "0 * * * * *";
        expected = "Esegue ogni minuto";
        assertEquals(expected, CronUtils.descriviCron(cron));
    }
}
```

### Test Execution
- Run unit tests: `./mvnw test`
- Run integration tests: `./mvnw verify`
- Run specific test class: `./mvnw test -Dtest=CronServiceTest`

### Test Guidelines
- Use `@SpringBootTest` for integration tests
- Use `@DataMongoTest` for isolated MongoDB tests
- Name test methods following the pattern: `should[ExpectedBehavior]When[StateUnderTest]`
- Use `@ParameterizedTest` for testing multiple inputs with the same logic
- Use `@Order` annotation to control test execution order when necessary

## Code Style & Development

### Cron Expression Conventions
- Always use 6 fields in cron expressions (with seconds)
- Prefer '?' instead of '*' for dayOfMonth/dayOfWeek when appropriate
- Use `CronUtils.descriviCron()` to generate human-readable descriptions of cron expressions

### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── it/algos/
│   │       ├── utility/    # Utility classes
│   │       │   └── schedule/  # Scheduling components
│   │       └── vbase/      # Base framework components
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── it/algos/
            └── schedule/   # Test classes for scheduling
```

### Frontend Development
- Vaadin 24.6.5 is used for the UI components
- TypeScript is used for frontend components
- Frontend resources are located in the `frontend/` directory
- Vaadin Maven plugin handles frontend build process

### Troubleshooting
- For MongoDB connection issues: verify `spring.data.mongodb.database` in application.properties
- Enable debug logging: `logging.level.it.algos=DEBUG` in application.properties
- Check application logs in `log/` directory

## Version Control
- Branch naming: `feature/`, `fix/`, `refactor/`
- Commit message format: `type(scope): description`
  ```
  feat(cron): add support for multiple expressions
  fix(mongo): correct DB connection in test environment
  ```

## Performance Considerations
- Use MongoDB indexes for frequently queried fields
- Implement caching for repetitive cron operations
- Use Spring's `@Scheduled` annotation for cron-based scheduling