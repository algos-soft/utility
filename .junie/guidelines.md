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
Here's an example of a simple test for the NotaEntity class:

```java
@SpringBootTest
class NotaEntityTest {

    @Test
    void testEntityCreation() {
        // Test entity creation using builder
        LocalDate today = LocalDate.now();
        NotaEntity nota = NotaEntity.builder()
                .typeLog(TypeLog.debug)
                .typeLevel(LogLevel.info)
                .inizio(today)
                .descrizione("Test note")
                .fatto(false)
                .build();

        // Verify properties
        assertEquals(TypeLog.debug, nota.getTypeLog());
        assertEquals(LogLevel.info, nota.getTypeLevel());
        assertEquals(today, nota.getInizio());
        assertEquals("Test note", nota.getDescrizione());
        assertFalse(nota.isFatto());
        assertNull(nota.getFine());
    }
}
```

### Test Execution
- Run all tests: `./mvnw test`
- Run integration tests: `./mvnw verify`
- Run specific test class: `./mvnw test -Dtest=CronServiceTest`
- Run specific test method: `./mvnw test -Dtest=NotaEntityTest#testEntityCreation`

### Test Guidelines
- Use `@SpringBootTest` for integration tests
- Use `@DataMongoTest` for isolated MongoDB tests
- Name test methods following the pattern: `test[Feature]` or `should[ExpectedBehavior]When[StateUnderTest]`
- Use `@ParameterizedTest` for testing multiple inputs with the same logic
- Use `@Order` annotation to control test execution order when necessary
- Add debug logging with `System.out.println("[DEBUG_LOG] message")` for troubleshooting

## Code Style & Development

### Java Conventions
- Use Lombok annotations to reduce boilerplate code (`@Data`, `@Builder`, etc.)
- Follow Spring component annotations best practices:
  - Use `@Service` for business logic components
  - Use `@Repository` for data access components
  - Use `@Controller` or `@RestController` for web endpoints
- Use static methods for utility functions that don't depend on instance state
- Use instance methods for operations that manipulate object state or depend on injected components

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
│   │       │   ├── nota/      # Note management
│   │       │   └── schedule/  # Scheduling components
│   │       └── vbase/      # Base framework components
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── it/algos/
            ├── utility/
            │   ├── nota/      # Tests for note components
            │   └── schedule/  # Tests for scheduling components
            └── vbase/      # Tests for base components
```

### Frontend Development
- Vaadin 24.3.9 is used for the UI components (as specified in application.properties)
- TypeScript is used for frontend components
- Frontend resources are located in the `frontend/` directory
- Vaadin Maven plugin handles frontend build process

### MongoDB Configuration
- Default database name: `utility` (configured in application.properties)
- Auto index creation is enabled: `spring.data.mongodb.auto-index-creation=true`
- Entity classes are annotated with `@Document(collection = "collectionName")`

### Troubleshooting
- For MongoDB connection issues: verify `spring.data.mongodb.database` in application.properties
- Enable debug logging: `logging.level.it.algos=DEBUG` in application.properties
- Check application logs in `log/` directory
- For Vaadin UI issues, check browser console for JavaScript errors

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
- Consider using `@Async` for long-running operations
