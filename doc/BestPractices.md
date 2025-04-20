# 📝 Best Practices

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

---

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

---

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

---

## 🔧 Static
In italiano, la convenzione più utilizzata e raccomandata è `public static final`.

Ecco le ragioni:

1. **Ordine standard**: La convenzione Java generalmente accettata suggerisce questo ordine:
    - Prima i modificatori di accesso (`public`, `private`, ecc.)
    - Poi i modificatori non di accesso (`static`, `final`, ecc.)

2. **JLS (Java Language Specification)**: Anche se tecnicamente l'ordine non influisce sul funzionamento del codice, il JLS suggerisce l'ordine `public static final`.

3. **Leggibilità**: È più facile leggere e comprendere il codice quando seguiamo convenzioni standard.

Esempio:
```java
// CORRETTO (raccomandato)
public static final String COSTANTE = "valore";

// FUNZIONA MA NON RACCOMANDATO
public final static String COSTANTE = "valore";
```


Inoltre, questo ordine è coerente con come appaiono le costanti nella libreria standard Java. Per esempio in `java.lang.Math`:
```java
public static final double PI = 3.14159265358979323846;
```

Quindi, per mantenere la coerenza con le convenzioni Java e migliorare la leggibilità del codice, è meglio utilizzare `public static final`.

---

## 🔧 Autowired
Per lo Spring Framework moderno, è meglio utilizzare `@Autowired`.

Ecco perché:

1. **@Autowired**:
    - È l'annotazione standard di Spring
    - Funziona perfettamente con tutto l'ecosistema Spring
    - È più flessibile e offre più opzioni di configurazione
    - È l'annotazione raccomandata nella documentazione Spring

2. **@Inject**:
    - È un'annotazione Jakarta EE (precedentemente Java EE)
    - È più generica e meno specifica per Spring
    - Non offre tutte le funzionalità specifiche di Spring

Se stai utilizzando Spring come framework principale, la scelta migliore è:

```java
@Autowired
private MioServizio servizio;
```

Inoltre, nelle versioni più recenti di Spring, se hai un solo costruttore, non è nemmeno necessario utilizzare `@Autowired` - l'iniezione delle dipendenze avverrà automaticamente.

La scelta di `@Autowired` garantisce:
- Migliore integrazione con Spring
- Accesso a tutte le funzionalità Spring
- Codice più coerente
- Migliore supporto degli strumenti di sviluppo

```
