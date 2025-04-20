# Guida Completa Live Templates in IntelliJ IDEA
<!-- TOC -->
* [Guida Completa Live Templates in IntelliJ IDEA](#guida-completa-live-templates-in-intellij-idea)
  * [Configurazione Base](#configurazione-base)
    * [Accesso alle Impostazioni](#accesso-alle-impostazioni)
    * [Creazione Gruppi](#creazione-gruppi)
    * [Creazione Template](#creazione-template)
  * [Organizzazione Gerarchica](#organizzazione-gerarchica)
    * [Struttura Consigliata](#struttura-consigliata)
    * [Convenzioni di Naming](#convenzioni-di-naming)
  * [Utilizzo Quotidiano](#utilizzo-quotidiano)
    * [Accesso Rapido](#accesso-rapido)
    * [Applicazione Template](#applicazione-template)
  * [Best Practices](#best-practices)
    * [Organizzazione Template](#organizzazione-template)
    * [Contesti di Applicazione](#contesti-di-applicazione)
    * [Esempi Pratici](#esempi-pratici)
  * [Ricerca e Filtri](#ricerca-e-filtri)
    * [Metodi di Ricerca](#metodi-di-ricerca)
    * [Filtri Automatici](#filtri-automatici)
  * [Gestione e Manutenzione](#gestione-e-manutenzione)
    * [Organizzazione](#organizzazione)
    * [Condivisione](#condivisione)
  * [Shortcut Essenziali](#shortcut-essenziali)
  * [Conversione Codice in Template](#conversione-codice-in-template)
  * [Note Finali](#note-finali)
  * [🔴 ToolTip](#-tooltip)
<!-- TOC -->
## Configurazione Base

### Accesso alle Impostazioni
1. Apri le impostazioni: `⌘` `,`
2. Naviga a: Editor > Live Templates

### Creazione Gruppi
1. Clicca `+` sulla destra
2. Seleziona "Template Group"
3. Assegna un nome significativo (es: Java-Test, Spring-MVC)

### Creazione Template
1. Seleziona il gruppo desiderato
2. Clicca `+`
3. Seleziona "Live Template"
4. Configura:
    - Abbreviazione (es: jt.setup)
    - Descrizione
    - Template
    - Contesto di applicazione

## Organizzazione Gerarchica

### Struttura Consigliata
- Java-Test
    - jt.setup
    - jt.assert
- Spring-Boot
    - sp.controller
    - sp.service
- Utility
    - util.logger
    - util.exception

### Convenzioni di Naming
- Prefisso gruppo + "." + nome template
- Esempi:
    - jt.assert    (Java Test)
    - sp.rest      (Spring)
    - util.log     (Utility)

## Utilizzo Quotidiano

### Accesso Rapido
- `⌘` `J` per aprire la lista template
- Inizia a digitare per filtrare:
    - Prefisso gruppo (es: "jt.")
    - Nome template
    - Parole nella descrizione

### Applicazione Template
1. Digita l'abbreviazione
2. Premi `⇥` per espandere
3. Usa `⇥` per navigare tra le variabili
4. `⏎` per completare

## Best Practices

### Organizzazione Template
1. Usa prefissi coerenti per gruppo
2. Aggiungi tag descrittivi [TEST], [SPRING]
3. Mantieni abbreviazioni concise
4. Definisci contesti appropriati

### Contesti di Applicazione
- Java: Class, Declaration, Statement
- XML, HTML, ecc.
- Test specifici
- Framework specifici

### Esempi Pratici
Gruppo: Java-Test
Template: Test Setup
Abbreviazione: jt.setup
Descrizione: [TEST] Setup base JUnit test
Contesto: Java-Declaration

Gruppo: Spring-MVC
Template: REST Controller
Abbreviazione: mvc.controller
Descrizione: [SPRING] Controller REST base
Contesto: Java-Declaration

## Ricerca e Filtri

### Metodi di Ricerca
1. Per gruppo: "jt." (mostra tutti i template Java Test)
2. Per tipo: "mvc." (mostra template Spring MVC)
3. Per keyword: "test" (cerca nella descrizione)

### Filtri Automatici
- Basati sul tipo di file
- Posizione del cursore
- Contesto del codice

## Gestione e Manutenzione

### Organizzazione
- Raggruppa per funzionalità
- Usa tag consistenti
- Mantieni descrizioni chiare
- Aggiorna regolarmente

### Condivisione
- Esportazione/Importazione
- Sincronizzazione con il team
- Backup regolari

## Shortcut Essenziali
- `⌘` `J`: Mostra lista template
- `⇥`: Espandi/Naviga avanti
- `⇧` `⇥`: Naviga indietro
- `⏎`: Completa inserimento

## Conversione Codice in Template
1. Seleziona il codice nell'editor
2. Code > Save as Live Template
3. Configura il nuovo template
4. Assegna al gruppo appropriato

## Note Finali
- Mantieni template aggiornati
- Rimuovi quelli non utilizzati
- Documenta modifiche importanti
- Condividi best practices con il team

## 🔴 ToolTip
Nella configurazione delle variabili occorre sempre usare le doppie virgolette. Anche per i numeri.
- `"1"`
- `"valore"`

 