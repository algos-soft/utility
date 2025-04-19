# Live Templates IntelliJ IDEA - Organizzazione Gerarchica

## 1. Java Core (<span style="color:#2ecc71">jc</span>)
- `test` - Template base per test JUnit
- `testpar` - Test parametrizzato
- `log` - Logger declaration
- `con` - Constructor
- `tostr` - ToString method
- `opt` - Optional handling
- `stream` - Stream operations
- `try` - Try-catch with resources

## 2. Spring Boot (<span style="color:#2ecc71">sb</span>)
### Controller (<span style="color:#2ecc71">sbc</span>)
- `rest` - REST Controller
- `get` - GET mapping
- `post` - POST mapping
- `put` - PUT mapping
- `del` - DELETE mapping

### Service (<span style="color:#2ecc71">sbs</span>)
- `serv` - Service class
- `async` - Async method
- `trans` - Transactional method

### Repository (<span style="color:#2ecc71">sbr</span>)
- `repo` - Repository interface
- `query` - Custom query method

### Configuration (<span style="color:#2ecc71">sbc</span>)
- `conf` - Configuration class
- `bean` - Bean definition
- `prop` - Properties class

## 3. Vaadin (<span style="color:#2ecc71">v</span>)
### Views (<span style="color:#2ecc71">vv</span>)
- `view` - Basic view
- `dialog` - Dialog view
- `form` - Form view
- `grid` - Grid view

### Components (<span style="color:#2ecc71">vc</span>)
- `btn` - Button setup
- `txt` - TextField setup
- `sel` - Select setup
- `grid` - Grid setup
- `layout` - Layout setup

### Binder (<span style="color:#2ecc71">vb</span>)
- `bind` - Binder setup
- `val` - Validator
- `conv` - Converter

## 4. Testing (<span style="color:#2ecc71">t</span>)
### Unit Test (<span style="color:#2ecc71">tu</span>)
- `test` - Unit test method
- `mock` - Mock setup
- `verify` - Verification block

### Integration Test (<span style="color:#2ecc71">ti</span>`)
- `boot` - SpringBootTest
- `web` - WebMvcTest
- `jpa` - JpaTest

## 5. Lombok (<span style="color:#2ecc71">l</span>)
- `data` - @Data class
- `build` - @Builder class
- `allargs` - @AllArgsConstructor
- `noargs` - @NoArgsConstructor
- `log` - @Slf4j

## 6. Annotation (<span style="color:#2ecc71">an</span>)

## 7. Algos (<span style="color:#2ecc71">al</span>)

## Configurazione in IntelliJ IDEA

1. Vai su `Settings/Preferences` -> `Editor` -> `Live Templates`
2. Crea i gruppi principali (Java Core, Spring Boot, etc.)
3. Per ogni template, definisci:
    - Abbreviazione (es. `sbc` per Spring Boot Controller)
    - Descrizione
    - Template text
    - Applicable context (es. Java declaration, statement, etc.)

