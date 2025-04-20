# Live Templates IntelliJ IDEA - Organizzazione Gerarchica
<!-- TOC -->
* [Live Templates IntelliJ IDEA - Organizzazione Gerarchica](#live-templates-intellij-idea---organizzazione-gerarchica)
  * [1. Java Core (<span style="color:#2ecc71">jc</span>)](#1-java-core-span-stylecolor2ecc71jcspan)
  * [2. Spring Boot (<span style="color:#2ecc71">sb</span>)](#2-spring-boot-span-stylecolor2ecc71sbspan)
    * [Controller (<span style="color:#2ecc71">sbc</span>)](#controller-span-stylecolor2ecc71sbcspan)
    * [Service (<span style="color:#2ecc71">sbs</span>)](#service-span-stylecolor2ecc71sbsspan)
    * [Repository (<span style="color:#2ecc71">sbr</span>)](#repository-span-stylecolor2ecc71sbrspan)
    * [Configuration (<span style="color:#2ecc71">sbc</span>)](#configuration-span-stylecolor2ecc71sbcspan)
  * [3. Vaadin (<span style="color:#2ecc71">v</span>)](#3-vaadin-span-stylecolor2ecc71vspan)
    * [Views (<span style="color:#2ecc71">vv</span>)](#views-span-stylecolor2ecc71vvspan)
    * [Components (<span style="color:#2ecc71">vc</span>)](#components-span-stylecolor2ecc71vcspan)
    * [Binder (<span style="color:#2ecc71">vb</span>)](#binder-span-stylecolor2ecc71vbspan)
  * [4. Testing (<span style="color:#2ecc71">t</span>)](#4-testing-span-stylecolor2ecc71tspan)
    * [Unit Test (<span style="color:#2ecc71">tu</span>)](#unit-test-span-stylecolor2ecc71tuspan)
    * [Integration Test (<span style="color:#2ecc71">ti</span>`)](#integration-test-span-stylecolor2ecc71tispan)
  * [5. Lombok (<span style="color:#2ecc71">l</span>)](#5-lombok-span-stylecolor2ecc71lspan)
  * [6. Annotation (<span style="color:#2ecc71">an</span>)](#6-annotation-span-stylecolor2ecc71anspan)
  * [7. Algos (<span style="color:#2ecc71">al</span>)](#7-algos-span-stylecolor2ecc71alspan)
  * [Configurazione in IntelliJ IDEA](#configurazione-in-intellij-idea)
<!-- TOC -->
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

