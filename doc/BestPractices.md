# ⚙️ Best Practices

## 🔧 Metodi statici
### Quando usare metodi statici:
1. Per utility methods che:
    - Non dipendono dallo stato dell'oggetto
    - Sono funzioni pure (stesso output per stesso input)
    - Non accedono a campi di istanza
    - Esempi: operazioni matematiche, parsing di stringhe, conversioni

2. Per Factory methods:
    - Metodi che creano istanze di oggetti
    - Esempio: `LocalDate.now()`

### Quando usare metodi di istanza:
1. Quando il metodo:
    - Manipola lo stato dell'oggetto
    - Accede a campi di istanza
    - Dipende da altre dipendenze iniettate (come nel tuo caso con `@Autowired`)
    - Fa parte della logica di business specifica dell'istanza

## 🔧 Component
### Differenza tra @SpringComponent e @Component
La differenza principale tra `@Component` e `@SpringComponent` è che:
1. `@Component` è l'annotazione standard di Spring Framework che viene utilizzata per indicare che una classe è un componente Spring. È inclusa nel package `org.springframework.stereotype.Component`.
2. `@SpringComponent` non è un'annotazione standard di Spring Framework. In realtà, questa annotazione non esiste nel framework Spring ufficiale.

### Caratteristiche di @Component
Le principali caratteristiche di `@Component` sono:
1. È un'annotazione generica per qualsiasi componente Spring
2. È la base per altre annotazioni stereotipo specializzate come:
    - (per la logica di business) `@Service`
    - `@Repository` (per l'accesso ai dati)
    - `@Controller` (per i controller web)
    - `@Configuration` (per le classi di configurazione)

### Utilizzo di @Component

 import org.springframework.stereotype.Component;

 @Component
 public class MioServizio {
 // implementazione
 }

Quando usi `@Component`:
- La classe viene automaticamente rilevata durante la scansione dei componenti [[1]](https://www.jetbrains.com/help/idea/2025.1/spring-diagrams.html#application-context-dependencies)
- Spring creerà e gestirà un bean per questa classe nel contesto dell'applicazione
- L'IDE mostrerà un'icona speciale nel gutter per indicare che è un bean Spring [[2]](https://www.jetbrains.com/help/idea/2025.1/spring-tool-window.html#bean-icons)

### Errori
Se qualcuno ti suggerisce di usare `@SpringComponent`, è probabilmente un errore o una confusione con `@Component`. Dovresti sempre usare `@Component` o una delle sue specializzazioni (, `@Repository`, `@Controller`) a seconda del caso d'uso specifico. `@Service`


## 🔧 Markdown
#### Formattazione esatta del testo
- Usando i triple backtick <code> ```:

```
Prima riga
Seconda riga
Terza riga
    Questa riga mantiene anche l'indentazione
```
- Usando il tag &lt;pre&gt; e &lt;/pre&gt;:
<pre>
Prima riga
Seconda riga
Terza riga
    Questa riga mantiene anche l'indentazione
</pre>

- Usando quattro spazi o un tab all'inizio di ogni riga:

      Prima riga
      Seconda riga
      Terza riga
      Questa riga mantiene anche l'indentazione

La soluzione più pulita e comunemente usata è la prima con i triple backtick, perché:
- È più leggibile nel codice sorgente
- È supportata universalmente
- Permette anche di specificare il linguaggio per la syntax highlighting (se necessario)
- Mantiene esattamente la formattazione del testo

