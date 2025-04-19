# 📝 Design pattern

Principali design pattern dalla <span style="color:#2ecc71">Gang of Four</span> (GoF)
- Erich Gamma
- Richard Helm
- Ralph Johnson
- John Vlissides.

I pattern sono divisi in tre categorie principali:
### 1. Pattern Creazionali
Gestiscono la creazione degli oggetti:
- **<span style="color:#3498db">Singleton</span>**
    - Garantisce che una classe abbia una sola istanza
    - Esempio:
```  
public class Singleton {
  private static Singleton instance;
  private Singleton() {}
    
  public static synchronized Singleton getInstance() {
      if (instance == null) {
          instance = new Singleton();
      }
      return instance;
  }
}
```
- **<span style="color:#3498db">Factory Method</span>**
    - Delega la creazione degli oggetti alle sottoclassi

- **<span style="color:#3498db">Abstract Factory</span>**
    - Crea famiglie di oggetti correlati

- **<span style="color:#3498db">Builder</span>**
    - Separa la costruzione di un oggetto complesso dalla sua rappresentazione

- **<span style="color:#3498db">Prototype</span>**
   - Crea nuovi oggetti clonando un prototipo

### 2. Pattern Strutturali
Gestiscono la composizione delle classi:
- **<span style="color:#3498db">Adapter</span>**
    - Permette a interfacce incompatibili di lavorare insieme

- **<span style="color:#3498db">Decorator</span>**
    - Aggiunge dinamicamente responsabilità a un oggetto

- **<span style="color:#3498db">Composite</span>**
    - Tratta allo stesso modo oggetti singoli e composizioni di oggetti

- **<span style="color:#3498db">Proxy</span>**
    - Fornisce un surrogato per controllare l'accesso a un oggetto

- **<span style="color:#3498db">Facade</span>**
    - Fornisce un'interfaccia unificata per un insieme di interfacce

- **<span style="color:#3498db">Bridge</span>**
    - Separa un'astrazione dalla sua implementazione

- **<span style="color:#3498db">Flyweight</span>**
    - Condivide efficientemente oggetti che vengono usati frequentemente

### 3. Pattern Comportamentali
Gestiscono l'interazione tra gli oggetti:
- **<span style="color:#3498db">Observer</span>**
    - Definisce una dipendenza uno-a-molti tra oggetti

- **<span style="color:#3498db">Strategy</span>**
    - Definisce una famiglia di algoritmi intercambiabili

- **<span style="color:#3498db">Command</span>**
    - Incapsula una richiesta come oggetto

- **<span style="color:#3498db">State</span>**
    - Permette a un oggetto di alterare il suo comportamento quando il suo stato interno cambia

- **<span style="color:#3498db">Template Method</span>**
    - Definisce lo scheletro di un algoritmo lasciando alcuni passi alle sottoclassi

- **<span style="color:#3498db">Iterator</span>**
    - Fornisce un modo per accedere sequenzialmente agli elementi di una collezione

- **<span style="color:#3498db">Mediator</span>**
    - Riduce l'accoppiamento tra gli oggetti facendoli comunicare indirettamente

- **<span style="color:#3498db">Chain of Responsibility</span>**
    - Passa una richiesta lungo una catena di handler

- **<span style="color:#3498db">Memento</span>**
    - Cattura e ripristina lo stato interno di un oggetto

- **<span style="color:#3498db">Visitor</span>**
    - Separa un algoritmo dalla struttura su cui opera

- **<span style="color:#3498db">Interpreter</span>**
    - Implementa un interprete per un linguaggio

### Esempio pratico di Observer Pattern:

```  
// Interfaccia per gli Observer
public interface Observer {
void update(String message);
}

// Subject (Observable)
public class NewsAgency {
private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    public void notifyObservers(String news) {
        for (Observer observer : observers) {
            observer.update(news);
        }
    }
}

// Observer concreto
public class NewsChannel implements Observer {
private String name;

    public NewsChannel(String name) {
        this.name = name;
    }
    
    @Override
    public void update(String news) {
        System.out.println(name + " ha ricevuto le news: " + news);
    }
}
```  

Questi pattern sono considerati le "best practice" per risolvere problemi comuni nello sviluppo software. La loro conoscenza è fondamentale per:
- Migliorare la manutenibilità del codice
- Rendere il codice più flessibile e riutilizzabile
- Facilitare la comunicazione tra sviluppatori
- Seguire principi SOLID

È importante notare che non è necessario utilizzare sempre tutti i pattern: la scelta dipende dalle specifiche esigenze del progetto.
